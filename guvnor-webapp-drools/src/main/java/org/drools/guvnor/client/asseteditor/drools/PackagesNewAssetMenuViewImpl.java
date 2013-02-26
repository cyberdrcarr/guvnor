/*
 * Copyright 2011 JBoss Inc
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

package org.drools.guvnor.client.asseteditor.drools;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

import org.drools.guvnor.client.common.AssetFormats;
import org.drools.guvnor.client.common.LoadingPopup;
import org.drools.guvnor.client.configurations.ApplicationPreferences;
import org.drools.guvnor.client.explorer.ClientFactory;
import org.drools.guvnor.client.messages.Constants;
import org.drools.guvnor.client.moduleeditor.drools.NewPackageWizard;
import org.drools.guvnor.client.resources.DroolsGuvnorImageResources;
import org.drools.guvnor.client.util.Util;

public class PackagesNewAssetMenuViewImpl implements PackagesNewAssetMenuView {

    private MenuBar createNewMenu = new MenuBar( true );
    private Presenter presenter;

    private MenuBar getMenu() {

        addNewPackageMenuItem();
        addNewChangeSetMenuItem();
        addNewSpringContextMenuItem();
        addNewServiceConfigMenuItem();
        addNewWorkingSetMenuItem();
        addNewRuleMenuItem();
        addNewRuleTemplateMenuItem();
        addNewPojoModelMenuItem();
        addNewDeclarativeModelMenuItem();
        addNewFunctionMenuItem();
        addNewDSLMenuItem();
        addNewPMMLMenuItem();
        addNewRuleFlowMenuItem();
        addNewBPMN2ProcessMenuItem();
        addNewWorkItemDefinitionMenuItem();
       	addNewFormDefinitionMenuItem();
        addNewEnumerationMenuItem();
        addNewTestScenarioMenuItem();
//        addNewSimulationTestMenuItem();
        addNewFileMenuItem();
        rebuildAllPackagesMenuItem();

        MenuBar rootMenuBar = new MenuBar( true );
        rootMenuBar.setAutoOpen( false );
        rootMenuBar.setAnimationEnabled( false );

        rootMenuBar.addItem( new MenuItem( Constants.INSTANCE.CreateNew(), createNewMenu ) );

        return rootMenuBar;
    }

    private void addItem(String text, boolean asHTML, Command command, String format) {
        MenuItem item = new MenuItem( text, asHTML, command );
        boolean enabled = ApplicationPreferences.enabledFormat( format );
        item.setEnabled( enabled );
        createNewMenu.addItem( item );
    }
    
    private void addNewFileMenuItem() {
    	createNewMenu.addItem(Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newFile(), Constants.INSTANCE.CreateAFile() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewFile();
                    }
                } );
    }

    private void addNewTestScenarioMenuItem() {
        addItem(Util.getHeader(DroolsGuvnorImageResources.INSTANCE.testManager(), Constants.INSTANCE.NewTestScenario()).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewTestScenario();
                    }
                },
                AssetFormats.TEST_SCENARIO);
    }

    private void addNewSimulationTestMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.simulationTestIcon(), Constants.INSTANCE.NewSimulationTest() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewSimulationTest();
                    }
                },
                AssetFormats.SIMULATION_TEST );
    }

    private void addNewEnumerationMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newEnumeration(), Constants.INSTANCE.NewEnumeration() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewEnumeration();
                    }
                },
                AssetFormats.ENUMERATION );
    }

    private void addNewWorkItemDefinitionMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newEnumeration(), Constants.INSTANCE.NewWorkitemDefinition() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewWorkitemDefinition();
                    }
                },
                AssetFormats.WORKITEM_DEFINITION );
    }

    private void addNewBPMN2ProcessMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.ruleflowSmall(), Constants.INSTANCE.NewBPMN2Process() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewBPMN2Process();
                    }
                },
                AssetFormats.BPMN2_PROCESS );
    }

    private void addNewFormDefinitionMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.formDefIcon(), Constants.INSTANCE.FormDefinition() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewFormDefinition();
                    }
                },
                AssetFormats.FORM_DEFINITION );
    }

    private void addNewRuleFlowMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.ruleflowSmall(), Constants.INSTANCE.NewRuleFlow() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewRuleFlow();
                    }
                },
                AssetFormats.RULE_FLOW_RF );
    }

    private void addNewDSLMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.dsl(), Constants.INSTANCE.NewDSL() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewDSL();
                    }
                }, 
                AssetFormats.DSL );
    }
    
    private void addNewPMMLMenuItem() {
        createNewMenu.addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.enumeration(), Constants.INSTANCE.UploadANewPMMLModel() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewPMMLModel();
                    }
                } );
    }

    private void addNewFunctionMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.functionAssets(), Constants.INSTANCE.NewFunction() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewFunction();
                    }
                }, 
                AssetFormats.FUNCTION );
    }

    private void addNewDeclarativeModelMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.modelAsset(), Constants.INSTANCE.NewDeclarativeModel() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewDeclarativeModel();
                    }
                }, 
                AssetFormats.DRL_MODEL );
    }

    private void addNewPojoModelMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.modelAsset(), Constants.INSTANCE.UploadPOJOModelJar() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewPojoModel();
                    }
                }, 
                AssetFormats.MODEL );
    }

    private void addNewRuleTemplateMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newTemplate(), Constants.INSTANCE.NewRuleTemplate() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewRuleTemplate();
                    }
                }, 
                AssetFormats.RULE_TEMPLATE );
    }

    private void addNewRuleMenuItem() {
        createNewMenu.addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.ruleAsset(), Constants.INSTANCE.NewRule() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewRule();
                    }
                } );
    }

    private void addNewWorkingSetMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newPackage(), Constants.INSTANCE.NewWorkingSet() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewWorkingSet();
                    }
                },
                AssetFormats.WORKING_SET );
    }

    private void addNewSpringContextMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newEnumeration(), Constants.INSTANCE.NewSpringContext() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewSpringContext();
                    }
                },
                AssetFormats.SPRING_CONTEXT );
    }

    private void addNewServiceConfigMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newEnumeration(), Constants.INSTANCE.NewServiceConfig() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewServiceConfig();
                    }
                },
                AssetFormats.SPRING_CONTEXT );
    }

    private void addNewPackageMenuItem() {
        createNewMenu.addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newPackage(), Constants.INSTANCE.NewPackage1() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewModule();
                    }
                } );
    }
    
    private void addNewChangeSetMenuItem() {
        addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.newEnumeration(), Constants.INSTANCE.NewChangeSet() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onNewChangeSet();
                    }
                }, 
                AssetFormats.CHANGE_SET );
    }

    public Widget asWidget() {
        return getMenu();
    }

    public void setPresenter( Presenter presenter ) {
        this.presenter = presenter;
    }

    public void openNewPackageWizard( ClientFactory clientFactory, EventBus eventBus) {
        NewPackageWizard wiz = new NewPackageWizard( clientFactory, eventBus );
        wiz.show();
    }

    public void openNewAssetWizardWithoutCategories( String format, ClientFactory clientFactory, EventBus eventBus  ) {
        openWizard( format, false, clientFactory, eventBus);
    }

    public void openNewAssetWizardWithCategories( String format, ClientFactory clientFactory, EventBus eventBus ) {
        openWizard( format, true, clientFactory, eventBus );
    }

    private void openWizard( String format, boolean showCategories, ClientFactory clientFactory, EventBus eventBus  ) {
        NewAssetWizard pop = new NewAssetWizard( showCategories, format, clientFactory, eventBus );

        pop.show();
    }

    public void confirmRebuild() {
        if ( Window.confirm( Constants.INSTANCE.RebuildConfirmWarning() ) ) {
            presenter.onRebuildConfirmed();
        }
    }

    public void showLoadingPopUpRebuildingPackageBinaries() {
        LoadingPopup.showMessage( Constants.INSTANCE.RebuildingPackageBinaries() );
    }

    public void closeLoadingPopUp() {
        LoadingPopup.close();
    }

    private void rebuildAllPackagesMenuItem() {
        createNewMenu.addItem( Util.getHeader( DroolsGuvnorImageResources.INSTANCE.refresh(), Constants.INSTANCE.RebuildAllPackageBinariesQ() ).asString(),
                true,
                new Command() {
                    public void execute() {
                        presenter.onRebuildAllPackages();
                    }
                } );
    }
}
