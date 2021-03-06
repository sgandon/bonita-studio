/**
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
package org.bonitasoft.studio.exporter.tests.bpmn;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

import org.bonitasoft.studio.common.ExpressionConstants;
import org.bonitasoft.studio.exporter.bpmn.transfo.BonitaToBPMN;
import org.bonitasoft.studio.exporter.extension.BonitaModelExporterImpl;
import org.bonitasoft.studio.exporter.extension.IBonitaModelExporter;
import org.bonitasoft.studio.model.connectorconfiguration.ConnectorConfiguration;
import org.bonitasoft.studio.model.connectorconfiguration.ConnectorParameter;
import org.bonitasoft.studio.model.expression.Expression;
import org.bonitasoft.studio.model.expression.Operation;
import org.bonitasoft.studio.model.process.Connector;
import org.bonitasoft.studio.model.process.Element;
import org.bonitasoft.studio.model.process.Lane;
import org.bonitasoft.studio.model.process.MainProcess;
import org.bonitasoft.studio.model.process.Pool;
import org.bonitasoft.studio.model.process.ServiceTask;
import org.bonitasoft.studio.model.process.diagram.edit.parts.MainProcessEditPart;
import org.bonitasoft.studio.test.swtbot.util.SWTBotTestUtil;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.spec.bpmn.di.util.DiResourceFactoryImpl;
import org.omg.spec.bpmn.model.DocumentRoot;

/**
 * @author Aurelien Pupier
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class BPMNConnectorExportImportTest extends SWTBotGefTestCase {
    final String connectorName = "connectorName";
    final String connectorDefVersion = "1.0.0";
    private static  MainProcess mainProcessAfterReimport;
    private static Connector connectorAfterReimport;
    private static boolean isInitalized = false;

    @Before
    public void init() throws IOException{
        if(!isInitalized){
            prepareTest();
        }
        isInitalized = true;
    }


    @Test
    public void testSingleConnectorOnServiceTask_version() throws IOException, ExecutionException{
        assertEquals("Connector definition version is not correct", connectorDefVersion, connectorAfterReimport.getDefinitionVersion());
    }

    @Test
    public void testSingleConnectorOnServiceTask_configuration_globalTextData() {
        final ConnectorConfiguration connectorConfiguration = connectorAfterReimport.getConfiguration();
        for (final ConnectorParameter connectorParameter : connectorConfiguration.getParameters()) {
            final String key = connectorParameter.getKey();
            if("from".equals(key)){
                final Expression expression = (Expression) connectorParameter.getExpression();
                assertEquals("The name of data referenced is not good","globalDataText", expression.getContent());
                assertEquals("Wrong type for a global variable data", ExpressionConstants.VARIABLE_TYPE, expression.getType());
                assertFalse("There shoudl be a data referenced", expression.getReferencedElements().isEmpty());
                return;
            }
        }
        fail("Missing parameter");
    }

    @Test
    public void testSingleConnectorOnServiceTask_configuration_transientTextData() {
        final ConnectorConfiguration connectorConfiguration = connectorAfterReimport.getConfiguration();
        for (final ConnectorParameter connectorParameter : connectorConfiguration.getParameters()) {
            final String key = connectorParameter.getKey();
            if("to".equals(key)){
                final Expression expression = (Expression) connectorParameter.getExpression();
                assertEquals("The name of data referenced is not good","transientDataText", expression.getContent());
                assertEquals("Wrong type for a global variable data", ExpressionConstants.VARIABLE_TYPE, expression.getType());
                assertFalse("There should be a data referenced", expression.getReferencedElements().isEmpty());
                return;
            }
        }
        fail("Missing parameter");
    }

    @Test
    public void testSingleConnectorOnServiceTask_configuration_constant() {
        final ConnectorConfiguration connectorConfiguration = connectorAfterReimport.getConfiguration();
        for (final ConnectorParameter connectorParameter : connectorConfiguration.getParameters()) {
            final String key = connectorParameter.getKey();
            if("subject".equals(key)){
                final Expression expression = (Expression) connectorParameter.getExpression();
                assertEquals("The name is not good","connectorParameterConstant", expression.getName());
                assertEquals("Wrong type for a global variable data", ExpressionConstants.CONSTANT_TYPE, expression.getType());
                return;
            }
        }
        fail("Missing parameter");
    }

    @Test
    public void testSingleConnectorOnServiceTask_output_constant() {
        for (final Operation operation : connectorAfterReimport.getOutputs()) {
            assertEquals("Opeator is not the right one", ExpressionConstants.ASSIGNMENT_OPERATOR, operation.getOperator().getType());
            final Expression rightOperand2 = operation.getRightOperand();
            if(rightOperand2 != null){
                if("connectorOuputConstant".equals(rightOperand2.getName())){
                    assertEquals(ExpressionConstants.CONSTANT_TYPE, rightOperand2.getType());
                    final Expression leftOperand = operation.getLeftOperand();
                    assertNotNull(leftOperand);
                    assertEquals(ExpressionConstants.VARIABLE_TYPE, leftOperand.getType());
                    assertFalse("There should be a referenced element", leftOperand.getReferencedElements().isEmpty());
                    return;
                }
                System.out.println("right operand not used"+rightOperand2.getName());
            }
        }
        fail("output operation not found for connectorOuputConstant");
    }

    @Test
    public void testSingleConnectorOnServiceTask_output_groovy() {
        for (final Operation operation : connectorAfterReimport.getOutputs()) {
            assertEquals("Operator is not the right one", ExpressionConstants.ASSIGNMENT_OPERATOR, operation.getOperator().getType());
            final Expression rightOperand = operation.getRightOperand();
            if(rightOperand != null){
                if("groovyExpression".equals(rightOperand.getName())){
                    assertEquals(ExpressionConstants.SCRIPT_TYPE, rightOperand.getType());
                    assertEquals("Wrong return type for Groovy connector output", String.class.getName(), rightOperand.getReturnType());
                    final Expression leftOperand = operation.getLeftOperand();
                    assertEquals(ExpressionConstants.VARIABLE_TYPE, leftOperand.getType());
                    assertFalse("There should be a referenced element", leftOperand.getReferencedElements().isEmpty());
                    return;
                }
                System.out.println("right operand not used"+rightOperand.getName());
            }
        }
        fail("output operation not found for connectorOuput Groovy");
    }

    @Test
    public void testSingleConnectorOnServiceTask_output_connectoroutput() {
        for (final Operation operation : connectorAfterReimport.getOutputs()) {
            assertEquals("Operator is not the right one", ExpressionConstants.ASSIGNMENT_OPERATOR, operation.getOperator().getType());
            final Expression rightOperand = operation.getRightOperand();
            if(rightOperand != null){
                if("isSent".equals(rightOperand.getName())){
                    assertEquals(ExpressionConstants.CONNECTOR_OUTPUT_TYPE, rightOperand.getType());
                    assertEquals("Wrong return type for Connector output", String.class.getName(), rightOperand.getReturnType());
                    final Expression leftOperand = operation.getLeftOperand();
                    assertEquals(ExpressionConstants.VARIABLE_TYPE, leftOperand.getType());
                    assertFalse("There should be a referenced element", leftOperand.getReferencedElements().isEmpty());
                    return;
                }
            }
        }
        fail("output operation not found for connectorOuput Groovy");
    }


    //TODO check connector parameter mapping with variable
    //TODO check connector parameter mapping with groovy script
    //TODO check connector output mapping

    protected void prepareTest() throws IOException {
        SWTBotTestUtil.importProcessWIthPathFromClass(bot, "diagramToTestConnectorBPMNImportExport-1.0.bos", "Bonita 6.x", "diagramToTestConnectorBPMNImportExport", BPMNConnectorExportImportTest.class, false);
        final SWTBotGefEditor editor1 = bot.gefEditor(bot.activeEditor().getTitle());
        final SWTBotGefEditPart step1Part = editor1.getEditPart("Step1").parent();
        final MainProcessEditPart mped = (MainProcessEditPart) step1Part.part().getRoot().getChildren().get(0);
        final IBonitaModelExporter exporter = new BonitaModelExporterImpl(mped) ;
        final File bpmnFileExported = File.createTempFile("testSingleConnectorOnServiceTask", ".bpmn");
        final boolean transformed = new BonitaToBPMN().transform(exporter, bpmnFileExported, new NullProgressMonitor());
        assertTrue("Error during export", transformed);


        final ResourceSet resourceSet1 = new ResourceSetImpl();
        final Map<String, Object> extensionToFactoryMap = resourceSet1.getResourceFactoryRegistry().getExtensionToFactoryMap();
        final DiResourceFactoryImpl diResourceFactoryImpl = new DiResourceFactoryImpl();
        extensionToFactoryMap.put("bpmn", diResourceFactoryImpl);
        final Resource resource2 = resourceSet1.createResource(URI.createFileURI(bpmnFileExported.getAbsolutePath()));
        resource2.load(Collections.emptyMap());

        final DocumentRoot model2 = (DocumentRoot) resource2.getContents().get(0);

        Display.getDefault().syncExec(new Runnable() {

            public void run() {
                try {
                    mainProcessAfterReimport = BPMNTestUtil.importBPMNFile(model2);
                } catch (final MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        for(final Element element : ((Lane)((Pool)mainProcessAfterReimport.getElements().get(0)).getElements().get(0)).getElements()){
            if(element instanceof ServiceTask){
                final ServiceTask serviceTask = (ServiceTask) element;
                connectorAfterReimport = serviceTask.getConnectors().get(0);
                break;
            }
        }
    }

    //	private AbstractProcess createProcessWithConnector() throws ExecutionException {
    //		NewDiagramCommandHandler newDiagramCommand = new NewDiagramCommandHandler();
    //		newDiagramCommand.execute(null);
    //
    //		final MainProcess mainProcess = newDiagramCommand.getNewDiagramFileStore().getContent();
    //		Pool process = (Pool) mainProcess.getElements().get(0);
    //
    //		Data processData = ProcessFactory.eINSTANCE.createData();
    //		processData.setDatasourceId("BOS");
    //		processData.setName("globalData");
    //		processData.setDataType(ModelHelper.getDataTypeForID(mainProcess, DataTypeLabels.stringDataType));
    //		process.getData().add(processData);
    //
    //		final Lane lane = (Lane)process.getElements().get(0);
    //		final EList<Element> elements = lane.getElements();
    //		Element activityToRemove = null;
    //		for(Element element : elements){
    //			if(element instanceof Activity){
    //				activityToRemove = element;
    //				elements.remove(element);
    //				break;
    //			}
    //		}
    //		elements.remove(activityToRemove);
    //		final Diagram diagramFor = ModelHelper.getDiagramFor(mainProcess);
    //		diagramFor.get
    //		GMFTools.convert(targetEClass, node, elementTypeResolver, editorType)
    //		final ServiceTask serviceTask = ProcessFactory.eINSTANCE.createServiceTask();
    //		Data localData = ProcessFactory.eINSTANCE.createData();
    //		localData.setDatasourceId("BOS");
    //		localData.setName("localData");
    //		localData.setDataType(ModelHelper.getDataTypeForID(mainProcess, DataTypeLabels.stringDataType));
    //		serviceTask.getData().add(localData);
    //		lane.getElements().add(serviceTask);
    //
    //		Connector connector = ProcessFactory.eINSTANCE.createConnector();
    //
    //		connector.setName(connectorName);
    //		connector.setDefinitionId("email");
    //
    //		connector.setDefinitionVersion(connectorDefVersion);
    //		connector.setEvent(ConnectorEvent.ON_ENTER.toString());
    //
    //		ConnectorConfiguration connectorConfiguration = ConnectorConfigurationFactory.eINSTANCE.createConnectorConfiguration();
    //		EList<ConnectorParameter> parameters = connectorConfiguration.getParameters();
    //		ConnectorParameter connectorParameter = ConnectorConfigurationFactory.eINSTANCE.createConnectorParameter();
    //		connectorParameter.setKey("from");
    //		Expression expression = ExpressionFactory.eINSTANCE.createExpression();
    //		expression.setContent("fromConstantValue");
    //		expression.setName("fromConstantValue");
    //
    //		connectorParameter.setExpression(expression);
    //
    //		parameters.add(connectorParameter);
    //		connector.setConfiguration(connectorConfiguration);
    //
    //		serviceTask.getConnectors().add(connector);
    //
    //
    //		return mainProcess;
    //	}

}
