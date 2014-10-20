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
package org.bonitasoft.studio.contract.core;

import org.bonitasoft.engine.bpm.contract.ComplexInputDefinition;
import org.bonitasoft.engine.bpm.contract.Type;
import org.bonitasoft.engine.bpm.contract.impl.ComplexInputDefinitionImpl;
import org.bonitasoft.engine.bpm.contract.impl.SimpleInputDefinitionImpl;
import org.bonitasoft.engine.bpm.process.impl.ActivityDefinitionBuilder;
import org.bonitasoft.engine.bpm.process.impl.ContractDefinitionBuilder;
import org.bonitasoft.engine.bpm.process.impl.UserTaskDefinitionBuilder;
import org.bonitasoft.studio.engine.contribution.BuildProcessDefinitionException;
import org.bonitasoft.studio.engine.contribution.IEngineDefinitionBuilder;
import org.bonitasoft.studio.model.process.Contract;
import org.bonitasoft.studio.model.process.ContractConstraint;
import org.bonitasoft.studio.model.process.ContractInput;
import org.bonitasoft.studio.model.process.ContractInputType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;

/**
 * @author Romain Bioteau
 *
 */
public class ContractEngineDefinitionBuilder implements IEngineDefinitionBuilder {

    private UserTaskDefinitionBuilder taskBuilder;
    private ActivityDefinitionBuilder activityDefinitionBuilder;
    private final ContractMappingFactory contractMappingBuilder;


    public ContractEngineDefinitionBuilder() {
        contractMappingBuilder = new ContractMappingFactory();
    }

    @Override
    public void build(final EObject element) throws BuildProcessDefinitionException {
        Assert.isNotNull(taskBuilder);
        Assert.isLegal(element instanceof Contract);
        final Contract contract = (Contract) element;

        final ContractDefinitionBuilder contractBuilder = taskBuilder.addContract();
        for (final ContractInput input : contract.getInputs()) {
            final Type inputType = getInputType(input);
            if (Type.COMPLEX == inputType) {
                addComplexInput(contractBuilder, input);
            } else {
                addSimpleInput(contractBuilder, input, inputType);
            }
        }
        for (final ContractConstraint constraint : contract.getConstraints()) {
            contractBuilder.addConstraint(constraint.getName(),
                    constraint.getExpression(),
                    constraint.getErrorMessage(),
                    constraint.getInputNames().toArray(new String[constraint.getInputNames().size()]));
        }

    }

    protected void addSimpleInput(final ContractDefinitionBuilder contractBuilder, final ContractInput input, final Type inputType) {
        contractBuilder.addSimpleInput(input.getName(), inputType,
                input.getDescription(), input.isMultiple());
        if (input.isMandatory()) {
            contractBuilder.addMandatoryConstraint(input.getName());
        }
        //addMappingOperation(input);
    }

    protected void addComplexInput(final ContractDefinitionBuilder contractBuilder, final ContractInput input) {
        final ComplexInputDefinition complexInput = buildComplexInput(input, contractBuilder);
        contractBuilder.addComplexInput(complexInput.getName(), complexInput.getDescription(), complexInput.isMultiple(), complexInput.getSimpleInputs(),
                complexInput.getComplexInputs());
    }

    protected ComplexInputDefinition buildComplexInput(final ContractInput input, final ContractDefinitionBuilder contractBuilder) {
        final ComplexInputDefinitionImpl complexInput = new ComplexInputDefinitionImpl(input.getName(), input.getDescription(), input.isMultiple());
        if (input.isMandatory()) {
            contractBuilder.addMandatoryConstraint(complexInput.getName());
        }
        for (final ContractInput child : input.getInputs()) {
            if (ContractInputType.COMPLEX == child.getType()) {
                complexInput.getComplexInputs().add(buildComplexInput(child, contractBuilder));
            } else {
                final Type inputType = getInputType(child);
                complexInput.getSimpleInputs().add(new SimpleInputDefinitionImpl(child.getName(), inputType, child.getDescription(), child.isMultiple()));
                if (child.isMandatory()) {
                    contractBuilder.addMandatoryConstraint(child.getName());
                }
            }
        }
        return complexInput;
    }

    public Type getInputType(final ContractInput input) {
        return org.bonitasoft.engine.bpm.contract.Type.valueOf(input.getType().getName());
    }

    //    protected void addMappingOperation(final ContractInput input) throws ContractCreationException {
    //        if (isMapped(input)) {
    //            try {
    //                final Operation storageOperation = contractMappingBuilder.createOperation(input.getMapping());
    //                activityDefinitionBuilder.addOperation(storageOperation);
    //            } catch (final OperationCreationException e) {
    //                throw new ContractCreationException("Failed to create mapping operation for input " + input.getName(), e);
    //            }
    //        }
    //    }
    //
    //    private boolean isMapped(final ContractInput input) {
    //        return input != null && input.getMapping() != null && input.getMapping().getData() != null;
    //    }

    public void setEngineBuilder(final UserTaskDefinitionBuilder builder) {
        taskBuilder = builder;
        activityDefinitionBuilder = builder;
    }

    @Override
    public boolean appliesTo(final EObject element) {
        return element instanceof Contract;
    }

    @Override
    public void setEngineBuilder(final Object engineBuilder) {
        if (engineBuilder instanceof UserTaskDefinitionBuilder) {
            taskBuilder = (UserTaskDefinitionBuilder) engineBuilder;
            activityDefinitionBuilder = (ActivityDefinitionBuilder) engineBuilder;
        }
    }

}