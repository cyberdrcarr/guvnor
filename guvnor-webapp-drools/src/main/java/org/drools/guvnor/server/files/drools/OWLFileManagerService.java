/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.server.files.drools;

import com.google.gwt.user.client.rpc.SerializationException;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;

import org.drools.compiler.DroolsParserException;
import org.drools.repository.ModuleItem;
import org.drools.repository.RulesRepository;
import javax.inject.Inject;
import javax.inject.Named;
import org.drools.guvnor.server.RepositoryCategoryService;
import org.drools.guvnor.server.files.FileManagerService;
import org.drools.guvnor.server.repository.Preferred;
import org.drools.guvnor.server.util.drools.OWLImporter;
import org.jboss.seam.security.annotations.LoggedIn;
import org.jboss.seam.security.Identity;

/**
 * 
 */
@Named("owlFileManager")
@ApplicationScoped
public class OWLFileManagerService {

    @Inject @Preferred
    private RulesRepository repository;

    @Inject
    private Identity identity;
    
    @Inject
    protected RepositoryCategoryService repositoryCategoryService;
    
    @Inject
    protected FileManagerService fileManagerService;

    
    @LoggedIn
    public String importOWL(InputStream owlStream) throws IOException,
                                                      DroolsParserException, 
                                                      SerializationException {
        
        OWLImporter importer = new OWLImporter(repository, repositoryCategoryService, fileManagerService, owlStream);
        
        String packageName = importer.getPackageName();
        
        if ( packageName == null || "".equals( packageName ) ) {
            throw new IllegalArgumentException( "Missing package name." );
        }
        
        boolean existing = repository.containsModule(packageName );

        // Check if the package is archived
        if ( existing && repository.isModuleArchived( packageName ) ) {
            // Remove the package so it can be created again.
            ModuleItem item = repository.loadModule( packageName );
            item.remove();
            existing = false;
        }
        
        
        ModuleItem pkg = null;
        
        if (existing ) {
            throw new IllegalArgumentException( "A package with the same name already exist." );
        }            

        pkg = repository.createModule( packageName,
                                            "<importedfom OWL>" );
        
        importer.processOWLDefinition(pkg);
        
        repository.save();
        
        return packageName;
    }


}