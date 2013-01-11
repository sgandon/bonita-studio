package org.bonitasoft.studio.test.swtbot.suite;

import org.bonitasoft.studio.actors.tests.SWTbot.ActorDefinitionTranslationsTest;
import org.bonitasoft.studio.actors.tests.SWTbot.ActorFilterDefinitionTest;
import org.bonitasoft.studio.actors.tests.SWTbot.ActorFilterDefinitionWizardPageTest;
import org.bonitasoft.studio.actors.tests.SWTbot.ActorFilterEditionTest;
import org.bonitasoft.studio.actors.tests.SWTbot.ActorFilterImplementationTest;
import org.bonitasoft.studio.actors.tests.exporter.SWTBotActorFilterExportTests;
import org.bonitasoft.studio.actors.tests.organization.OrganizationCreationTest;
import org.bonitasoft.studio.common.extension.BonitaStudioExtensionRegistryManager;
import org.bonitasoft.studio.common.jface.FileActionDialog;
import org.bonitasoft.studio.common.log.BonitaStudioLog;
import org.bonitasoft.studio.configuration.test.swtbot.TestConfigurationDialog;
import org.bonitasoft.studio.connectors.test.exporter.SWTBotConnectorExportTests;
import org.bonitasoft.studio.connectors.test.swtbot.ConnectorDefinitionTranslationsTest;
import org.bonitasoft.studio.connectors.test.swtbot.ConnectorDefinitionWizardPageTest;
import org.bonitasoft.studio.connectors.test.swtbot.ConnectorEditionTest;
import org.bonitasoft.studio.connectors.test.swtbot.ConnectorImplementationTest;
import org.bonitasoft.studio.connectors.test.swtbot.SWTBotConnectorDefinitionTest;
import org.bonitasoft.studio.connectors.test.swtbot.TestLoadSaveConnectorConfiguration;
import org.bonitasoft.studio.connectors.test.swtbot.TestTextAreaInConnectorWizard;
import org.bonitasoft.studio.diagram.test.DiagramTests;
import org.bonitasoft.studio.diagram.test.FormsDiagramTests;
import org.bonitasoft.studio.diagram.test.TestOpenDiagram;
import org.bonitasoft.studio.diagram.test.TestUndoRedoStackLimit;
import org.bonitasoft.studio.preferences.BonitaPreferenceConstants;
import org.bonitasoft.studio.preferences.BonitaStudioPreferencesPlugin;
import org.bonitasoft.studio.properties.test.SearchIndexesTest;
import org.bonitasoft.studio.properties.test.TestThrowCatchMessage;
import org.bonitasoft.studio.tests.IHeapDumper;
import org.bonitasoft.studio.tests.bug.TestBugsSWTBot;
import org.bonitasoft.studio.tests.data.TestData;
import org.bonitasoft.studio.tests.pagetemplate.TestPageTemplate;
import org.bonitasoft.studio.tests.processzoo.examples.TestWebPurchase;
import org.bonitasoft.studio.util.test.BonitaTestSuite;
import org.bonitasoft.studio.validators.test.swtbot.TestAddValidatorToProcessAndRun;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.internal.browser.WebBrowserUIPlugin;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Copyright (C) 2009 BonitaSoft S.A.
 * BonitaSoft, 31 rue Gustave Eiffel - 38000 Grenoble
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



@RunWith(BonitaTestSuite.class)
@Suite.SuiteClasses({
    TestBugsSWTBot.class,
    DiagramTests.class,
    TestConfigurationDialog.class,
    OrganizationCreationTest.class,
    ActorDefinitionTranslationsTest.class,
    ActorFilterDefinitionTest.class,
    ActorFilterDefinitionWizardPageTest.class,
    ActorFilterEditionTest.class,
    ActorFilterImplementationTest.class,
    SWTBotActorFilterExportTests.class,
    ConnectorDefinitionTranslationsTest.class,
    ConnectorEditionTest.class,
    ConnectorDefinitionWizardPageTest.class,
    ConnectorImplementationTest.class,
    SearchIndexesTest.class,
    TestLoadSaveConnectorConfiguration.class,
    TestTextAreaInConnectorWizard.class,
    SWTBotConnectorExportTests.class,
    SWTBotConnectorDefinitionTest.class,
    TestThrowCatchMessage.class,
    TestWebPurchase.class,
    TestAddValidatorToProcessAndRun.class,
    FormsDiagramTests.class,
    TestPageTemplate.class,
    TestData.class,
    TestUndoRedoStackLimit.class,
    TestOpenDiagram.class
})
public class AllSWTBotTests {


    @BeforeClass
    public static void setUp() {
        BonitaStudioPreferencesPlugin.getDefault().getPreferenceStore().setValue(BonitaPreferenceConstants.CONSOLE_BROWSER_CHOICE, BonitaPreferenceConstants.INTERNAL_BROWSER);
        WebBrowserUIPlugin.getInstance().getPreferenceStore().setValue(BonitaPreferenceConstants.CONSOLE_BROWSER_CHOICE, BonitaPreferenceConstants.INTERNAL_BROWSER);
        FileActionDialog.setDisablePopup(true);
    }

    @AfterClass
    public static void tearDown() {
        for(IConfigurationElement elem : BonitaStudioExtensionRegistryManager.getInstance().getConfigurationElements("org.bonitasoft.studio.tests.heapdump")){
            IHeapDumper dumper;
            try {
                dumper = (IHeapDumper) elem.createExecutableExtension("class");
                dumper.dumpHeap(AllSWTBotTests.class.getSimpleName()+".hprof", false);
            } catch (CoreException e) {
                BonitaStudioLog.error(e);
            }
        }
    }

}
