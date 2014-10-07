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
package org.bonitasoft.studio.contract.ui.property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.jface.viewers.CellNavigationStrategy;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * @author Romain Bioteau
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ContracInputTableViewerCellNavigationStrategyTest {

    private ContracInputTableViewerCellNavigationStrategy contracInputTableViewerCellNavigationStrategy;

    @Mock
    private TableViewer viewer;

    @Mock
    private ContractInputController controller;

    @Mock
    private ViewerCell currentSelectedCell;

    @Mock
    private Table table;

    @Mock
    private ViewerCell viewerCell;

    @Mock
    private ViewerRow viewerRow;

    @Mock
    private TableItem tableItem;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        contracInputTableViewerCellNavigationStrategy = spy(new ContracInputTableViewerCellNavigationStrategy(viewer, controller));
        when(viewer.getTable()).thenReturn(table);
        when(currentSelectedCell.getNeighbor(anyInt(), anyBoolean())).thenReturn(viewerCell);
        when(viewerCell.getViewerRow()).thenReturn(viewerRow);
        when(viewerRow.getItem()).thenReturn(tableItem);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void should_isNavigationEvent_return_true_if_key_is_CR_or_DEL_or_ARROWS() throws Exception {
        final Event event = new Event();
        event.keyCode = SWT.CR;
        assertThat(contracInputTableViewerCellNavigationStrategy.isNavigationEvent(viewer, event)).isTrue();
        event.keyCode = SWT.DEL;
        assertThat(contracInputTableViewerCellNavigationStrategy.isNavigationEvent(viewer, event)).isTrue();
        event.keyCode = SWT.ARROW_RIGHT;
        assertThat(contracInputTableViewerCellNavigationStrategy.isNavigationEvent(viewer, event)).isTrue();
        event.keyCode = 'a';
        assertThat(contracInputTableViewerCellNavigationStrategy.isNavigationEvent(viewer, event)).isFalse();
    }

    @Test
    public void should_findSelectedCell_not_add_a_new_input_on_Enter_if_below_row_exists() throws Exception {
        when(currentSelectedCell.getNeighbor(anyInt(), anyBoolean())).thenReturn(viewerCell);
        final Event event = new Event();
        event.keyCode = SWT.CR;
        contracInputTableViewerCellNavigationStrategy.findSelectedCell(viewer, currentSelectedCell, event);
        verify(controller,never()).addInput(viewer);
        verify(table).setSelection(tableItem);
    }

    @Test
    public void should_findSelectedCell_add_a_new_input_on_Enter_if_below_row_not_exists() throws Exception {
        when(currentSelectedCell.getNeighbor(anyInt(), anyBoolean())).thenReturn(null);
        final Event event = new Event();
        event.keyCode = SWT.CR;
        contracInputTableViewerCellNavigationStrategy.findSelectedCell(viewer, currentSelectedCell, event);
        verify(controller).addInput(viewer);
    }

    @Test
    public void should_findSelectedCell_not_add_a_new_input_on_Enter_if_not_on_first_column() throws Exception {
        when(currentSelectedCell.getNeighbor(anyInt(), anyBoolean())).thenReturn(null);
        when(currentSelectedCell.getColumnIndex()).thenReturn(1);
        final Event event = new Event();
        event.keyCode = SWT.CR;
        contracInputTableViewerCellNavigationStrategy.findSelectedCell(viewer, currentSelectedCell, event);
        verify(controller, never()).addInput(viewer);
    }

    @Test
    public void should_findSelectedCell_remove_input_on_Delete() throws Exception {
        final Event event = new Event();
        event.keyCode = SWT.DEL;
        contracInputTableViewerCellNavigationStrategy.findSelectedCell(viewer, currentSelectedCell, event);
        verify(controller).removeInput(viewer);
    }

    @Test
    public void should_findSelectedCell_call_super() throws Exception {
        final Event event = new Event();
        event.keyCode = SWT.ARROW_LEFT;
        contracInputTableViewerCellNavigationStrategy.findSelectedCell(viewer, null, event);
        verify((CellNavigationStrategy) contracInputTableViewerCellNavigationStrategy).findSelectedCell(viewer, null, event);

        contracInputTableViewerCellNavigationStrategy.findSelectedCell(viewer, currentSelectedCell, event);
        verify((CellNavigationStrategy) contracInputTableViewerCellNavigationStrategy).findSelectedCell(viewer, currentSelectedCell, event);
    }

}
