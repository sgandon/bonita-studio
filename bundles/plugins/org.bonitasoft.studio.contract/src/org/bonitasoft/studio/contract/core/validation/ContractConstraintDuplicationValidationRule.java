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
package org.bonitasoft.studio.contract.core.validation;

import java.util.HashSet;
import java.util.Set;

import org.bonitasoft.studio.contract.ContractPlugin;
import org.bonitasoft.studio.contract.i18n.Messages;
import org.bonitasoft.studio.model.process.Contract;
import org.bonitasoft.studio.model.process.ContractConstraint;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.ecore.EObject;


/**
 * @author Romain Bioteau
 *
 */
public class ContractConstraintDuplicationValidationRule implements IValidationRule {

    protected static final String DUPLICATED_CONSTRAINT_ID = "duplicate_constraint";

    @Override
    public boolean appliesTo(final EObject element) {
        return element instanceof Contract;
    }

    @Override
    public IStatus validate(final EObject element) {
        final Contract contract = (Contract) element;
        final MultiStatus status = new MultiStatus(ContractPlugin.PLUGIN_ID, IStatus.OK, "", null);

        final Set<String> result = new HashSet<String>();
        final Set<String> duplicated = new HashSet<String>();
        for (final ContractConstraint child : contract.getConstraints()) {
            if (child.getName() != null
                    && !child.getName().isEmpty()
                    && !result.add(child.getName())) {
                duplicated.add(child.getName());
            }
        }
        for (final String dup : duplicated) {
            status.add(ValidationStatus.error(dup));
        }
        return status;
    }


    @Override
    public String getRuleId() {
        return DUPLICATED_CONSTRAINT_ID;
    }

    @Override
    public String getMessage(final IStatus status) {
        Assert.isLegal(status != null);
        if (status.isMultiStatus()) {
            final StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(" ");
            for (final IStatus child : status.getChildren()) {
                errorMessage.append("\"" + child.getMessage() + "\"");
                errorMessage.append(", ");
            }
            String error = errorMessage.toString();
            error = error.substring(0, error.length() - 2);
            return Messages.bind(Messages.duplicatedConstraintNames, error);
        }
        return status.getMessage();
    }

}