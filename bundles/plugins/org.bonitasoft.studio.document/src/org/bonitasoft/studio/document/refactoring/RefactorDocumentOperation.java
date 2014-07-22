/**
 * Copyright (C) 2014 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.studio.document.refactoring;

import java.util.List;

import org.bonitasoft.studio.common.ExpressionConstants;
import org.bonitasoft.studio.common.emf.tools.ModelHelper;
import org.bonitasoft.studio.model.expression.Expression;
import org.bonitasoft.studio.model.expression.ExpressionPackage;
import org.bonitasoft.studio.model.process.Document;
import org.bonitasoft.studio.model.process.Pool;
import org.bonitasoft.studio.model.process.ProcessPackage;
import org.bonitasoft.studio.refactoring.core.AbstractRefactorOperation;
import org.bonitasoft.studio.refactoring.core.AbstractScriptExpressionRefactoringAction;
import org.bonitasoft.studio.refactoring.core.RefactoringOperationType;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

public class RefactorDocumentOperation extends AbstractRefactorOperation<Document, Document, DocumentRefactorPair> {

    public RefactorDocumentOperation(final RefactoringOperationType operationType) {
        super(operationType);
    }

    @Override
    protected void doExecute(final IProgressMonitor monitor) {
        updateDocumentInDocumentExpressions(compoundCommand);
        for (final DocumentRefactorPair pairToRefactor : pairsToRefactor) {
            final Pool container = getContainer(pairToRefactor.getOldValue());
            final List<Document> documents = container.getDocuments();
            final int index = documents.indexOf(pairToRefactor.getOldValue());
            compoundCommand.append(RemoveCommand.create(domain, container, ProcessPackage.Literals.POOL__DOCUMENTS, pairToRefactor.getOldValue()));
            compoundCommand.append(AddCommand.create(domain, container, ProcessPackage.Literals.POOL__DOCUMENTS, pairToRefactor.getNewValue(), index));
        }
    }

    protected void updateDocumentInDocumentExpressions(final CompoundCommand cc) {
        for (final DocumentRefactorPair pairToRefactor : pairsToRefactor) {
            final List<Expression> expressions = ModelHelper.getAllItemsOfType(getContainer(pairToRefactor.getOldValue()),
                    ExpressionPackage.Literals.EXPRESSION);
            for (final Expression exp : expressions) {
                if ((ExpressionConstants.DOCUMENT_TYPE.equals(exp.getType())
                        || ExpressionConstants.CONSTANT_TYPE.equals(exp.getType())
                        || ExpressionConstants.DOCUMENT_REF_TYPE.equals(exp.getType()))
                        && exp.getName() != null
                        && exp.getName()
                        .equals(
                                pairToRefactor
                                .getOldValue()
                                .getName())) {
                    // update name and content
                    cc.append(SetCommand.create(domain, exp, ExpressionPackage.Literals.EXPRESSION__NAME, pairToRefactor.getNewValue().getName()));
                    cc.append(SetCommand.create(domain, exp, ExpressionPackage.Literals.EXPRESSION__CONTENT, pairToRefactor.getNewValue().getName()));
                }
            }
        }
    }

    @Override
    protected AbstractScriptExpressionRefactoringAction<DocumentRefactorPair> getScriptExpressionRefactoringAction(
            final List<DocumentRefactorPair> pairsToRefactor,
            final List<Expression> scriptExpressions,
            final List<Expression> refactoredScriptExpression,
            final CompoundCommand compoundCommand,
            final EditingDomain domain,
            final RefactoringOperationType operationType) {
        return new DocumentScriptExpressionRefactoringAction(pairsToRefactor, scriptExpressions, refactoredScriptExpression, compoundCommand, domain,
                operationType);
    }

    @Override
    protected DocumentRefactorPair createRefactorPair(final Document newItem, final Document oldItem) {
        return new DocumentRefactorPair(newItem, oldItem);
    }

    @Override
    protected Pool getContainer(final Document oldValue) {
        return (Pool) ModelHelper.getParentProcess(oldValue);
    }

}