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

package org.drools.guvnor.client.common;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

/**
 * This form style class is to be extended to provide "form style" dialogs (eg
 * in a popup).
 */
public class FormStyleLayout extends Composite {

    private FlexTable layout = new FlexTable();
    private FlexCellFormatter formatter = layout.getFlexCellFormatter();
    private int numInLayout = 0;

    /**
     * This has no header
     */
    public FormStyleLayout() {
        initWidget( layout );
    }

    /**
     * Create a new layout with a header and and icon.
     */
    public FormStyleLayout( final Image image,
                            final String title ) {
        addHeader( image,
                   title );
        initWidget( layout );
    }

    /**
     * Create a new layout with a header.
     */
    public FormStyleLayout( final String title ) {
        addHeader( title );
        initWidget( layout );
    }

    /**
     * Clears the layout table.
     */
    public void clear() {
        numInLayout = 0;
        this.layout.clear();
    }

    /**
     * Add a widget to the "form"
     * @param lbl The label displayed in column 0
     * @param editor The Widget displayed in column 1
     * @return Index of row created
     */
    public int addAttribute( final String lbl,
                             final Widget editor ) {
        String id = DOM.createUniqueId();
        if ( editor instanceof CheckBox ) {
            editor.getElement().getFirstChildElement().setAttribute( "aria-labelledby", id );
            editor.getElement().getFirstChildElement().setAttribute( "aria-required", String.valueOf( true ) );
            editor.getElement().getFirstChildElement().setTabIndex( 0 );
        } else {
            editor.getElement().setAttribute( "aria-labelledby", id );
            editor.getElement().setAttribute( "aria-required", String.valueOf( true ) );
            editor.getElement().setTabIndex( 0 );
        }

        int row = numInLayout;
        HTML label = new HTML( "<div class='form-field' id=" + id + ">" + lbl + "</div>" );
        layout.setWidget( numInLayout,
                          0,
                          label );
        formatter.setAlignment( numInLayout,
                                0,
                                HasHorizontalAlignment.ALIGN_RIGHT,
                                HasVerticalAlignment.ALIGN_MIDDLE );
        layout.setWidget( numInLayout,
                          1,
                          editor );
        formatter.setAlignment( numInLayout,
                                1,
                                HasHorizontalAlignment.ALIGN_LEFT,
                                HasVerticalAlignment.ALIGN_MIDDLE );

        numInLayout++;
        return row;
    }

    /**
     * Add a widget to the "form"
     * @param lbl The label displayed in column 0
     * @param editor The Widget displayed in column 1
     * @param isVisible Is the new row visible
     * @return Index of row created
     */
    public int addAttribute( final String lbl,
                             final Widget editor,
                             final boolean isVisible ) {
        int rowIndex = addAttribute( lbl,
                                     editor );
        setAttributeVisibility( rowIndex,
                                isVisible );
        return rowIndex;
    }

    /**
     * Add a widget to the "form" across an entire row
     * @param w The Widget displayed in column 1
     * @return Index of row created
     */
    public int addRow( final Widget w ) {
        //TODO ARIA: what to do with widget has no visible label?

        int row = numInLayout;
        layout.setWidget( numInLayout,
                          0,
                          w );
        formatter.setColSpan( numInLayout,
                              0,
                              2 );
        numInLayout++;
        return row;
    }

    /**
     * Set the visibility of an Attribute
     * @param row
     * @param isVisible
     */
    public void setAttributeVisibility( final int row,
                                        final boolean isVisible ) {
        layout.getWidget( row,
                          0 ).setVisible( isVisible );
        layout.getWidget( row,
                          1 ).setVisible( isVisible );
    }

    /**
     * Adds a header at the top.
     */
    protected void addHeader( final Image image,
                              final String title ) {
        HTML name = new HTML( "<div class='form-field'><b>" + title + "</b></div>" );
        name.setStyleName( "resource-name-Label" );
        doHeader( image,
                  name );
    }

    private void doHeader( final Image image,
                           final Widget title ) {
        //The image in FormStyleLayout is merely for the purpose of decoration, no need to set it's alt text for section 508.
        image.setAltText( "" );
        layout.setWidget( 0,
                          0,
                          image );
        formatter.setAlignment( 0,
                                0,
                                HasHorizontalAlignment.ALIGN_LEFT,
                                HasVerticalAlignment.ALIGN_MIDDLE );
        layout.setWidget( 0,
                          1,
                          title );
        numInLayout++;
    }

    /**
     * Adds a header at the top.
     */
    protected void addHeader( final String title ) {
        HTML name = new HTML( "<div class='form-field'><b>" + title + "</b></div>" );
        name.setStyleName( "resource-name-Label" );
        doHeader( name );
    }

    private void doHeader( Widget title ) {
        layout.setWidget( 0,
                          1,
                          title );
        numInLayout++;
    }

    protected void addHeader( final Image image,
                              final String title,
                              final Widget titleIcon ) {
        HTML name = new HTML( "<div class='form-field'><b>" + title + "</b></div>" );
        name.setStyleName( "resource-name-Label" );
        HorizontalPanel horiz = new HorizontalPanel();
        horiz.add( name );
        horiz.add( titleIcon );
        doHeader( image,
                  horiz );

    }

    public void setFlexTableWidget( final int row,
                                    final int col,
                                    final Widget widget ) {
        layout.setWidget( row,
                          col,
                          widget );
    }

    public int getNumAttributes() {
        return numInLayout;
    }

    public Widget getWidget() {
        return super.getWidget();
    }

}
