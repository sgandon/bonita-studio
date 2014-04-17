/**
 * Copyright (C) 2014 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.studio.data.operation;

import static org.assertj.core.api.Assertions.assertThat;

import org.bonitasoft.studio.common.ExpressionConstants;
import org.bonitasoft.studio.common.emf.tools.ExpressionHelper;
import org.bonitasoft.studio.common.refactoring.RefactoringOperationType;
import org.bonitasoft.studio.model.expression.Expression;
import org.bonitasoft.studio.model.expression.ExpressionFactory;
import org.bonitasoft.studio.model.expression.Operation;
import org.bonitasoft.studio.model.expression.assertions.ExpressionAssert;
import org.bonitasoft.studio.model.process.AbstractProcess;
import org.bonitasoft.studio.model.process.Data;
import org.bonitasoft.studio.model.process.ProcessFactory;
import org.bonitasoft.studio.model.process.Task;
import org.bonitasoft.studio.model.process.util.ProcessAdapterFactory;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Romain Bioteau
 * 
 */
public class RefactorDataOperationTest {

    private AbstractProcess parentProcess;

    private Data myData;

    private Expression leftOperand;

    private Expression rightOperand;

    private EditingDomain domain;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        domain = new AdapterFactoryEditingDomain(new ProcessAdapterFactory(), new BasicCommandStack());
        parentProcess = ProcessFactory.eINSTANCE.createPool();
        myData = ProcessFactory.eINSTANCE.createData();
        myData.setName("myData");
        parentProcess.getData().add(myData);
        Task task = ProcessFactory.eINSTANCE.createTask();
        Operation operation = ExpressionFactory.eINSTANCE.createOperation();
        leftOperand = ExpressionFactory.eINSTANCE.createExpression();
        leftOperand.setName(myData.getName());
        leftOperand.setContent(myData.getName());
        leftOperand.setType(ExpressionConstants.VARIABLE_TYPE);
        leftOperand.getReferencedElements().add(ExpressionHelper.createDependencyFromEObject(myData));
        rightOperand = ExpressionFactory.eINSTANCE.createExpression();
        rightOperand.setName("getData");
        rightOperand.setContent("return " + myData.getName() + ";");
        rightOperand.setType(ExpressionConstants.SCRIPT_TYPE);
        rightOperand.getReferencedElements().add(ExpressionHelper.createDependencyFromEObject(myData));
        operation.setLeftOperand(leftOperand);
        operation.setRightOperand(rightOperand);
        task.getOperations().add(operation);
        parentProcess.getElements().add(task);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void should_run_refactor_data_in_script_expression() throws Exception {
        Data newData = ProcessFactory.eINSTANCE.createData();
        newData.setName("refactored");
        RefactorDataOperation refacorDataOperation = new RefactorDataOperation(RefactoringOperationType.UPDATE);
        refacorDataOperation.setAskConfirmation(false);// Skip UI
        refacorDataOperation.setEditingDomain(domain);
        refacorDataOperation.setContainer(parentProcess);
        refacorDataOperation.setOldData(myData);
        refacorDataOperation.setNewData(newData);
        refacorDataOperation.run(null);
        ExpressionAssert.assertThat(leftOperand).hasName(newData.getName());
        ExpressionAssert.assertThat(rightOperand).hasContent("return " + newData.getName() + ";");
        EList<EObject> referencedElements = rightOperand.getReferencedElements();
        assertThat(referencedElements).hasSize(1);
        EObject dep = referencedElements.get(0);
        assertThat(dep).isInstanceOf(Data.class);
        assertThat(((Data) dep).getName()).isEqualTo("refactored");
    }
}