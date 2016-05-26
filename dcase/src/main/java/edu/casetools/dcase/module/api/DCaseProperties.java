/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of DCASE (Design for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the design of a Context-Aware System (C-AS). 
 * 
 * DCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCASE. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.module.api;

/**
 * The Class DCaseProperties contains the name of each of the properties from
 * the property pages.
 */

public class DCaseProperties {

    /****************************************************************************
     * Common properties
     **************************************************************************/

    public static final String PROPERTY_NAME = "Name";

    /****************************************************************************
     * Context Information Message related properties
     **************************************************************************/
    // Situational Parameter
    public static final String PROPERTY_MESSAGE_ID = "CETagId";

    public static final String PROPERTY_MESSAGE_RESPONSIBILITY = "TagResponsibility";

    public static final String PROPERTY_MESSAGE_REGULARITY = "TagRegularity";

    public static final String PROPERTY_MESSAGE_FREQUENCY = "TagFrequency";

    public static final String PROPERTY_MESSAGE_SYNCHRONICITY = "TagSynchronicity";

    private DCaseProperties() {

    }

}
