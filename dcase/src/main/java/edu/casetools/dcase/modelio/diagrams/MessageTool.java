/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of DCASE (Design for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the requirements elicitation stage of a Context-Aware System (C-AS). 
 * 
 * DCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCASE.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.modelio.diagrams;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelio.api.modelio.diagram.IDiagramGraphic;
import org.modelio.api.modelio.diagram.IDiagramHandle;
import org.modelio.api.modelio.diagram.IDiagramLink;
import org.modelio.api.modelio.diagram.ILinkPath;
import org.modelio.api.modelio.diagram.InvalidDestinationPointException;
import org.modelio.api.modelio.diagram.InvalidPointsPathException;
import org.modelio.api.modelio.diagram.InvalidSourcePointException;
import org.modelio.api.modelio.diagram.tools.DefaultLinkTool;
import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.metamodel.factory.ExtensionNotFoundException;
import org.modelio.metamodel.uml.behavior.interactionModel.Message;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.dcase.module.i18n.I18nMessageService;
import edu.casetools.dcase.module.impl.DCaseModule;

/**
 * The Class NoteTool has the common methods to create the tool of a note.
 */
public abstract class MessageTool extends DefaultLinkTool {

    private static final Logger logger = Logger.getLogger(MessageTool.class.getName());

    /**
     * Default method to accept the Note when creating it.
     *
     * @param representation
     *            the diagram handler
     * @param targetNode
     *            the target node where the element is going to be represented
     * @return true, if the target element is accepted
     */
    /*
     * IDiagramHandle is forced by DefaultAttachedBoxTool
     */
    protected boolean defaultAcceptElement(IDiagramHandle representation, IDiagramGraphic targetNode) { // NOSONAR
	MObject target = targetNode.getElement();
	return (target instanceof ModelElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.modelio.api.diagram.tools.DefaultAttachedBoxTool#actionPerformed(
     * org.modelio.api.diagram.IDiagramHandle,
     * org.modelio.api.diagram.IDiagramGraphic,
     * org.modelio.api.diagram.IDiagramLink.LinkRouterKind,
     * org.modelio.api.diagram.ILinkPath, org.eclipse.draw2d.geometry.Point)
     */
    @Override
    public void actionPerformed(IDiagramHandle representation, IDiagramGraphic origin, IDiagramGraphic target,
	    IDiagramLink.LinkRouterKind kind, ILinkPath path) {
	IModelingSession session = DCaseModule.getInstance().getModuleContext().getModelingSession();
	ITransaction transaction = session
		.createTransaction(I18nMessageService.getString("Info.Session.Create", new String[] { "" }));

	try {
	    ModelElement originElement = (ModelElement) origin.getElement();
	    ModelElement targetElement = (ModelElement) target.getElement();

	    Message message = null;
	    try {
		message = createOwnCommunicationMessage(session, originElement, targetElement);
	    } catch (ExtensionNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    List<IDiagramGraphic> graphics = representation.unmask(message, 0, 0);

	    for (IDiagramGraphic graphic : graphics) {
		createLink(kind, path, graphic);
	    }

	    representation.save();
	    representation.close();
	    transaction.commit();
	    transaction.close();
	} finally {
	    transaction.close();
	}
    }

    /**
     * Creates the own note.
     * 
     * @param session
     *
     * @param model
     *            the model
     * @param owner
     *            the owner
     * @return the note
     * @throws ExtensionNotFoundException
     *             the extension not found exception
     */
    protected abstract Message createOwnCommunicationMessage(IModelingSession session, ModelElement model,
	    ModelElement owner) throws ExtensionNotFoundException;

    private void createLink(IDiagramLink.LinkRouterKind kind, ILinkPath path, IDiagramGraphic graphic) {
	IDiagramLink link = (IDiagramLink) graphic;
	if (null != link) {
	    link.setRouterKind(kind);
	    try {
		link.setPath(path);
	    } catch (InvalidPointsPathException | InvalidSourcePointException | InvalidDestinationPointException e) {
		logger.log(Level.SEVERE, e.getMessage(), e);
	    }
	}
    }

}
