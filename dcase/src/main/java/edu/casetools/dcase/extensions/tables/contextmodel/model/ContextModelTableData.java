/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of RCASE (Requirements for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the requirements elicitation stage of a Context-Aware System (C-AS). 
 * 
 * RCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RCASE.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.extensions.tables.contextmodel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.dcase.extensions.tables.headers.DataTypes;
import edu.casetools.dcase.extensions.tables.headers.TableHeaderData;
import edu.casetools.dcase.module.api.DCaseProperties;
import edu.casetools.dcase.module.api.DCaseStereotypes;
import edu.casetools.dcase.module.i18n.I18nMessageService;
import edu.casetools.dcase.module.impl.DCasePeerModule;
import edu.casetools.dcase.utils.ModelioUtils;
import edu.casetools.dcase.utils.tables.ModelioTableUtils;
import edu.casetools.dcase.utils.tables.TableUtils;

/**
 * The Class DependencyTableData.
 */
public class ContextModelTableData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -191731257462245537L;

    private String scope;

    /** The data list. */
    protected ArrayList<ContextModelTableRow> dataList;
    private ArrayList<TableHeaderData> headers;
    private int columnNumber;

    public ContextModelTableData() {
	dataList = new ArrayList<>();
	headers = new ArrayList<>();
	createHeaders();
    }

    public List<TableHeaderData> getHeaders() {
	return headers;
    }

    public void setHeaders(List<TableHeaderData> headers) {
	this.headers = (ArrayList<TableHeaderData>) headers;
    }

    /**
     * Instantiates a new dependency table data.
     */

    private void createHeaders() {
	String[] columnNames = { "Table.MessagesTable.Header.Name", "Table.MessagesTable.Header.Id",
		"Table.MessagesTable.Header.Responsibility", "Table.MessagesTable.Header.Regularity",
		"Table.MessagesTable.Header.Frequency", "Table.MessagesTable.Header.Synchronicity" };
	String[] properties = { DCaseProperties.PROPERTY_NAME, DCaseProperties.MESSAGE_ID,
		DCaseProperties.MESSAGE_RESPONSIBILITY, DCaseProperties.MESSAGE_REGULARITY,
		DCaseProperties.MESSAGE_FREQUENCY, DCaseProperties.MESSAGE_SYNCHRONICITY };
	DataTypes[] dataTypes = { DataTypes.STRING_TYPE, DataTypes.STRING_TYPE, DataTypes.COMBOBOX_TYPE,
		DataTypes.COMBOBOX_TYPE, DataTypes.STRING_TYPE, DataTypes.COMBOBOX_TYPE };

	if ((columnNames.length == properties.length) && (columnNames.length == dataTypes.length)
		&& (dataTypes.length == properties.length)) {
	    columnNumber = columnNames.length;
	    for (int i = 0; i < columnNames.length; i++) {
		headers.add(createTableHeaderData(columnNames[i], properties[i], dataTypes[i]));
	    }
	}
    }

    private TableHeaderData createTableHeaderData(String columnName, String property, DataTypes dataType) {
	return new TableHeaderData(I18nMessageService.getString(columnName), property, dataType);
    }

    /**
     * Gets the data list.
     *
     * @return the data list
     */
    public List<ContextModelTableRow> getDataList() {
	return dataList;
    }

    public int getColumnCount() {
	return columnNumber;
    }

    /**
     * Sets the data list.
     *
     * @param dataList
     *            the new data list
     */
    public void setDataList(List<ContextModelTableRow> dataList) {
	this.dataList = (ArrayList<ContextModelTableRow>) dataList;
    }

    /**
     * New data list.
     *
     * @return the int
     */
    public int newDataList() {
	int size = 0;
	if (null != dataList)
	    size = dataList.size();
	dataList = new ArrayList<>();
	return size;
	// Returns old data list size
    }

    public int getRowCount() {
	return dataList.size();
    }

    public void setScope(String selection) {
	this.scope = selection;
	this.update();
    }

    private void updateScope() {
	MObject element = ModelioUtils.getInstance().getElementByName(scope);
	ArrayList<MObject> messages = new ArrayList<>();
	messages = (ArrayList<MObject>) ModelioTableUtils.getInstance().getMessagesFromComInteraction(messages,
		(ModelElement) element);
	dataList = new ArrayList<>();
	setContextAttributes(messages);
    }

    private void setContextAttributes(ArrayList<MObject> contextAttribute) {
	for (int i = 0; i < contextAttribute.size(); i++) {
	    ContextModelTableRow row = new ContextModelTableRow(contextAttribute.get(i));
	    dataList.add(row);
	}
    }

    private void updateAll() {
	dataList = new ArrayList<>();
	ArrayList<MObject> list = new ArrayList<>();
	list = (ArrayList<MObject>) TableUtils.getInstance().getAllElementsStereotypedAs(list,
		DCasePeerModule.MODULE_NAME, DCaseStereotypes.CONTEXT_INFORMATION_MESSAGE);
	setContextAttributes(list);
    }

    public void update() {
	if (scope.equals(I18nMessageService.getString("Menu.ContextModel.Combobox.All")))
	    updateAll();
	else
	    updateScope();

    }

}
