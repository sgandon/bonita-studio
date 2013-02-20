package org.bonitasoft.studio.actors.tests.SWTbot;

/*
 * Copyright (C) 2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import junit.framework.Assert;

import org.bonitasoft.studio.actors.i18n.Messages;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

/**
 * @author Aur�lie Zara
 * 
 */

public class SWTBotActorFilterUtil {

	/**
	 * use it to access to the Actor filter wizard "New actor filter definition"
	 * (menu Development>Actor filters>New implementation)
	 * 
	 * @param bot
	 */
	public static void activateActorFilterDefinitionShell(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Development")),10000);
		bot.menu("Development").menu("Actor filters").menu("New definition...")
		.click();
		bot.waitUntil(Conditions.shellIsActive(Messages.newFilterDefinition),10000);
	}

	/**
	 * use it to access to the Actor filter wizard
	 * "New actor filter implementation" (menu DEvelopement>Actor filters>New
	 * implementation)
	 * 
	 * @param bot
	 */
	public static void activateActorFilterImplementationShell(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Development")),10000);
		bot.menu("Development").menu("Actor filters")
		.menu("New implementation...").click();
		bot.waitUntil(Conditions
				.shellIsActive(Messages.newFilterImplementation),10000);
	}

	/**
	 * use it to access to the manage organization wizard "Manage Organization"
	 * (menu Organization>Manage...)
	 * 
	 * @param bot
	 */
	public static void activateNewOrganizationWizard(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Organization")),10000);
		bot.menu("Organization").menu("Manage...").click();
		bot.waitUntil(Conditions.shellIsActive(Messages.manageOrganizationTitle),10000);
	}

	/**
	 * use it to access to the export organization dialog "Export Organization"
	 * (menu Organization>Export...)
	 * 
	 * @param bot
	 */
	public static void activateExportOrganizationWizard(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Organization")),10000);
		bot.menu("Organization").menu("Export...").click();
		bot.waitUntil(Conditions.shellIsActive(Messages.exportOrganizationTitle),10000);
	}

	/**
	 * use it to access to the synchronize organization wizard
	 * "Manage Organization" (menu Organization>Manage...)
	 * 
	 * @param bot
	 */
	public static void activateSynchronizeOrganizationWizard(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Organization")),10000);
		bot.menu("Organization").menu("Publish...").click();
		bot.waitUntil(Conditions.shellIsActive(Messages.synchronizeOrganizationTitle),10000);
	}

	/**
	 * use it to access to the Actor filter wizard "Edit definition" (menu
	 * DEvelopement>Actor filters>Edit definition)
	 * 
	 * @param bot
	 */
	public static void activateActorFilterDefEditionShell(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Development")),10000);
		bot.menu("Development").menu("Actor filters")
		.menu("Edit definition...").click();
		bot.waitUntil(Conditions
				.shellIsActive("Select an actor filter definition"),10000);
	}

	/**
	 * use it to access to the Actor filter wizard "Edit definition" (menu
	 * DEvelopement>Actor filters>Edit definition)
	 * 
	 * @param bot
	 */
	public static void activateActorFilterImplEditionShell(SWTBot bot) {
		bot.waitUntil(Conditions.widgetIsEnabled(bot.menu("Development")),10000);
		bot.menu("Development").menu("Actor filters")
		.menu("Edit implementation...").click();
		bot.waitUntil(Conditions
				.shellIsActive("Select an actor filter implementation"),10000);
	}

	/**
	 * use it when the wizard "New  definition" is active. (menu
	 * development>Actor filters>New definition...)
	 * 
	 * @param bot
	 * @param id
	 * @param version
	 */
	public static void createActorFilterDefinition(final SWTBot bot, final String id,
			final String version) {
		bot.textWithLabel("Definition id *").setText(id);
		bot.waitUntil(new ICondition() {
			
			@Override
			public boolean test() throws Exception {
				// TODO Auto-generated method stub
				return id.equals(bot.textWithLabel("Definition id *").getText());
			}
			
			@Override
			public void init(SWTBot bot) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getFailureMessage() {
				// TODO Auto-generated method stub
				return null;
			}
		},10000);
		bot.textWithLabel("Version *").setText(version);
bot.waitUntil(new ICondition() {
			
			@Override
			public boolean test() throws Exception {
				// TODO Auto-generated method stub
				return version.equals(bot.textWithLabel("Version *").getText());
			}
			
			@Override
			public void init(SWTBot bot) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getFailureMessage() {
				// TODO Auto-generated method stub
				return null;
			}
		},10000);
	}

	/**
	 * use it when the wizard "New  definition" is active. (menu
	 * development>Actors filters>New definition...)
	 * 
	 * @param bot
	 * @param categoryId
	 * @throws Exception
	 */
	public static void createNewCategory(SWTBot bot, String categoryId)
			throws Exception {
		bot.waitUntil(Conditions.widgetIsEnabled((bot.button("New..."))),10000);
		bot.button("New...").click();
		Assert.assertFalse("ok button should be desabled",
				bot.button(IDialogConstants.OK_LABEL).isEnabled());
		bot.textWithLabel("Id").setText(categoryId);
		bot.textWithLabel("Display name").setText(categoryId);
		bot.button(IDialogConstants.OK_LABEL).click();
	}

	/**
	 * use it when the wizard "New  definition" is active. (menu
	 * development>Actors filters>New definition...)
	 * 
	 * @param bot
	 * @param categoryId
	 * @throws Exception
	 */
	public static void addCategory(SWTBot bot) {
		SWTBotShell shell = bot.activeShell();
		shell.activate();
		Assert.assertFalse("ok button should be desabled",
				bot.button(IDialogConstants.OK_LABEL).isEnabled());
		bot.table().select(0);
		bot.button(IDialogConstants.OK_LABEL).click();

	}

	/**
	 * use it to access to the wizard "Export connector" (menu
	 * Development<Connector>Export)
	 */
	public static void activateExportActorFilterShell(SWTBot bot) {
		bot.menu("Development").menu("Actor filters").menu("Export...").click();
		bot.waitUntil(Conditions.shellIsActive("Export connector"));
		bot.activeShell().setFocus();
	}

	/**
	 * use it to create a connector def and impl (no window should be opened)
	 * 
	 * @param bot
	 * @param id
	 * @param version
	 * @param className
	 * @param packageName
	 */
	public static void createActorFilterDefAndImpl(final SWTWorkbenchBot bot,
			String id, String version, String className, String packageName) {
		final int nbEditorsBefore = bot.editors().size();
		activateActorFilterDefinitionShell(bot);
		createActorFilterDefinition(bot, id, version);
		bot.button(IDialogConstants.FINISH_LABEL).click();
		activateActorFilterImplementationShell(bot);
		bot.textWithLabel("Implementation id *").setText(id);
		bot.comboBoxWithLabel("Definition *").setSelection(
				id + " (" + version + ")");
		bot.textWithLabel("Class name *").setText(className);
		bot.textWithLabel("Package *").setText(packageName);
		bot.button(IDialogConstants.FINISH_LABEL).click();
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				return nbEditorsBefore + 1 == bot.editors().size();
			}

			public void init(SWTBot bot) {
			}

			public String getFailureMessage() {
				return "Editor for implementation has not been opened.";
			}
		}, 30000);
	}
}
