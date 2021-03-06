/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of R-CASE (Requirements for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the requirements elicitation phase of a Context-Aware System (C-AS). 
 * 
 * R-CASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * R-CASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Modelio.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.utils.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ListModel;

import org.modelio.api.modelio.model.IMetamodelExtensions;
import org.modelio.metamodel.uml.behavior.communicationModel.CommunicationInteraction;
import org.modelio.metamodel.uml.behavior.usecaseModel.UseCase;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.metamodel.uml.infrastructure.Stereotype;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.dcase.module.impl.DCaseModule;
import edu.casetools.dcase.module.impl.DCasePeerModule;
import edu.casetools.dcase.utils.ModelioUtils;

/**
 * The Class TableUtils.
 */
public class TableUtils {

    private static TableUtils instance = null;

    /**
     * Gets the single instance of TableUtils.
     *
     * @return single instance of TableUtils
     */
    public static TableUtils getInstance() {
	if (instance == null) {
	    instance = new TableUtils();
	}
	return instance;
    }

    /**
     * Gets the header.
     *
     * @param stereotypeVector
     *            the stereotype vector
     * @return the header
     */
    public List<MObject> getHeader(List<Stereotype> stereotypeVector) {

	List<MObject> linesHeader = new ArrayList<>();

	for (Stereotype stereotype : stereotypeVector) {
	    linesHeader = getAllElementsStereotypedAs(linesHeader, DCasePeerModule.MODULE_NAME, stereotype.getName());
	}

	return linesHeader;
    }

    /**
     * Gets the all elements stereotyped as.
     *
     * @param list
     *            the list
     * @param stereotype
     *            the stereotype
     * @return the all elements stereotyped as
     */
    public List<MObject> getAllElementsStereotypedAs(List<MObject> list, String module, String stereotype) {
	List<MObject> allElements = ModelioUtils.getInstance().getAllElements();

	for (MObject object : allElements) {
	    if ((object instanceof ModelElement) && (((ModelElement) object).isStereotyped(module, stereotype)))
		list.add(object);
	}

	return list;
    }

    /**
     * Gets the all dependencies stereotypes.
     *
     * @return the all dependencies stereotypes
     */
    public List<Stereotype> getAllDependenciesStereotypes() {

	ArrayList<Stereotype> stereotypes = new ArrayList<>();
	Collection<Dependency> dependencies = getAllDependencies();

	for (Dependency dependency : dependencies) {
	    stereotypes = getStereotypesFromDependency(stereotypes, dependency);
	}

	return stereotypes;
    }

    /**
     * Gets the all dependencies.
     *
     * @return the all dependencies
     */
    public Collection<Dependency> getAllDependencies() {
	return DCaseModule.getInstance().getModuleContext().getModelingSession().findByClass(Dependency.class);
    }

    private ArrayList<Stereotype> getStereotypesFromDependency(ArrayList<Stereotype> list, Dependency dependency) {

	for (Stereotype stereotype : dependency.getExtension()) {
	    if (!list.contains(stereotype))
		list.add(stereotype);
	}

	return list;
    }

    /**
     * Gets the dependency stereotpye from name.
     *
     * @param stereotypeName
     *            the stereotype name
     * @return the dependency stereotpye from name
     */
    public Stereotype getDependencyStereotpyeFromName(String stereotypeName) {
	IMetamodelExtensions stereotypes = DCaseModule.getInstance().getModuleContext().getModelingSession()
		.getMetamodelExtensions();
	return stereotypes.getStereotype(DCasePeerModule.MODULE_NAME, stereotypeName,
		DCaseModule.getInstance().getModuleContext().getModelioServices().getMetamodelService().getMetamodel()
			.getMClass(Dependency.class));

    }

    /**
     * Gets the stereotypes from names.
     *
     * @param stereotypeNames
     *            the stereotype names
     * @return the stereotypes from names
     */
    public List<Stereotype> getStereotypesFromNames(ListModel<String> stereotypeNames) {
	IMetamodelExtensions stereotypes = DCaseModule.getInstance().getModuleContext().getModelingSession()
		.getMetamodelExtensions();
	Stereotype stereotype;
	ArrayList<Stereotype> xStereotypes = new ArrayList<>();
	for (int i = 0; i < stereotypeNames.getSize(); i++) {
	    stereotype = stereotypes.getStereotype(DCasePeerModule.MODULE_NAME, stereotypeNames.getElementAt(i),
		    DCaseModule.getInstance().getModuleContext().getModelioServices().getMetamodelService()
			    .getMetamodel().getMClass(Class.class));
	    if (null != stereotype)
		xStereotypes.add(stereotype);
	}

	for (int i = 0; i < stereotypeNames.getSize(); i++) {
	    stereotype = stereotypes.getStereotype(DCasePeerModule.MODULE_NAME, stereotypeNames.getElementAt(i),
		    DCaseModule.getInstance().getModuleContext().getModelioServices().getMetamodelService()
			    .getMetamodel().getMClass(UseCase.class));
	    if (null != stereotype)
		xStereotypes.add(stereotype);
	}

	return xStereotypes;
    }

    /**
     * Gets the dependency table longer string.
     *
     * @param list
     *            the list
     * @return the dependency table longer string
     */
    public String getDependencyTableLongerString(List<MObject> list) {
	String longerString = "";
	if (null != list && !list.isEmpty()) {
	    for (MObject header : list) {
		if (header.getName().length() > longerString.length())
		    longerString = header.getName();
	    }
	}
	return longerString;
    }

    /**
     * Gets the container table longer string.
     *
     * @param list
     *            the list
     * @return the container table longer string
     */
    public String getContainerTableLongerString(List<Object> list) {
	String longerString = "";
	if (null != list && !list.isEmpty()) {
	    for (Object header : list) {
		if (((String) header).length() > longerString.length())
		    longerString = (String) header;
	    }
	}
	return longerString;
    }

    public Collection<CommunicationInteraction> getCommunicationInteractions() {
	return DCaseModule.getInstance().getModuleContext().getModelingSession()
		.findByClass(CommunicationInteraction.class);
    }

}
