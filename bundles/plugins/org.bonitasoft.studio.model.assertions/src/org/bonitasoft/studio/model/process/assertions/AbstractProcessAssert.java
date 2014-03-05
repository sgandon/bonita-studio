package org.bonitasoft.studio.model.process.assertions;

import static java.lang.String.format;

import java.util.Date;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.bonitasoft.studio.model.configuration.Configuration;
import org.bonitasoft.studio.model.parameter.Parameter;
import org.bonitasoft.studio.model.process.AbstractProcess;
import org.bonitasoft.studio.model.process.Actor;
import org.bonitasoft.studio.model.process.Connection;
import org.bonitasoft.studio.model.process.DataType;

/**
 * {@link AbstractProcess} specific assertions - Generated by CustomAssertionGenerator.
 */
public class AbstractProcessAssert extends AbstractAssert<AbstractProcessAssert, AbstractProcess> {

    /**
     * Creates a new </code>{@link AbstractProcessAssert}</code> to make assertions on actual AbstractProcess.
     * @param actual the AbstractProcess we want to make assertions on.
     */
    public AbstractProcessAssert(AbstractProcess actual) {
        super(actual, AbstractProcessAssert.class);
    }

    /**
     * An entry point for AbstractProcessAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(myAbstractProcess)</code> and get specific assertion with code completion.
     * @param actual the AbstractProcess we want to make assertions on.
     * @return a new </code>{@link AbstractProcessAssert}</code>
     */
    public static AbstractProcessAssert assertThat(AbstractProcess actual) {
        return new AbstractProcessAssert(actual);
    }

    /**
     * Verifies that the actual AbstractProcess's actors contains the given Actor elements.
     * @param actors the given elements that should be contained in actual AbstractProcess's actors.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's actors does not contain all given Actor elements.
     */
    public AbstractProcessAssert hasActors(Actor... actors) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // check that given Actor varargs is not null.
        if (actors == null)
            throw new AssertionError("Expecting actors parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getActors()).contains(actors);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess has no actors.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's actors is not empty.
     */
    public AbstractProcessAssert hasNoActors() {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected :\n  <%s>\nnot to have actors but had :\n  <%s>", actual, actual.getActors());

        // check
        if (!actual.getActors().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's author is equal to the given one.
     * @param author the given author to compare the actual AbstractProcess's author to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AbstractProcess's author is not equal to the given one.
     */
    public AbstractProcessAssert hasAuthor(String author) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected <%s> author to be:\n  <%s>\n but was:\n  <%s>", actual, author, actual.getAuthor());

        // check
        if (!actual.getAuthor().equals(author)) {
            throw new AssertionError(errorMessage);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's categories contains the given String elements.
     * @param categories the given elements that should be contained in actual AbstractProcess's categories.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's categories does not contain all given String elements.
     */
    public AbstractProcessAssert hasCategories(String... categories) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // check that given String varargs is not null.
        if (categories == null)
            throw new AssertionError("Expecting categories parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getCategories()).contains(categories);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess has no categories.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's categories is not empty.
     */
    public AbstractProcessAssert hasNoCategories() {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected :\n  <%s>\nnot to have categories but had :\n  <%s>", actual, actual.getCategories());

        // check
        if (!actual.getCategories().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's configurations contains the given Configuration elements.
     * @param configurations the given elements that should be contained in actual AbstractProcess's configurations.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's configurations does not contain all given Configuration elements.
     */
    public AbstractProcessAssert hasConfigurations(Configuration... configurations) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // check that given Configuration varargs is not null.
        if (configurations == null)
            throw new AssertionError("Expecting configurations parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getConfigurations()).contains(configurations);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess has no configurations.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's configurations is not empty.
     */
    public AbstractProcessAssert hasNoConfigurations() {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected :\n  <%s>\nnot to have configurations but had :\n  <%s>", actual, actual.getConfigurations());

        // check
        if (!actual.getConfigurations().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's connections contains the given Connection elements.
     * @param connections the given elements that should be contained in actual AbstractProcess's connections.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's connections does not contain all given Connection elements.
     */
    public AbstractProcessAssert hasConnections(Connection... connections) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // check that given Connection varargs is not null.
        if (connections == null)
            throw new AssertionError("Expecting connections parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getConnections()).contains(connections);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess has no connections.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's connections is not empty.
     */
    public AbstractProcessAssert hasNoConnections() {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected :\n  <%s>\nnot to have connections but had :\n  <%s>", actual, actual.getConnections());

        // check
        if (!actual.getConnections().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's creationDate is equal to the given one.
     * @param creationDate the given creationDate to compare the actual AbstractProcess's creationDate to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AbstractProcess's creationDate is not equal to the given one.
     */
    public AbstractProcessAssert hasCreationDate(Date creationDate) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected <%s> creationDate to be:\n  <%s>\n but was:\n  <%s>", actual, creationDate, actual.getCreationDate());

        // check
        if (!actual.getCreationDate().equals(creationDate)) {
            throw new AssertionError(errorMessage);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's datatypes contains the given DataType elements.
     * @param datatypes the given elements that should be contained in actual AbstractProcess's datatypes.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's datatypes does not contain all given DataType elements.
     */
    public AbstractProcessAssert hasDatatypes(DataType... datatypes) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // check that given DataType varargs is not null.
        if (datatypes == null)
            throw new AssertionError("Expecting datatypes parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getDatatypes()).contains(datatypes);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess has no datatypes.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's datatypes is not empty.
     */
    public AbstractProcessAssert hasNoDatatypes() {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected :\n  <%s>\nnot to have datatypes but had :\n  <%s>", actual, actual.getDatatypes());

        // check
        if (!actual.getDatatypes().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's modificationDate is equal to the given one.
     * @param modificationDate the given modificationDate to compare the actual AbstractProcess's modificationDate to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AbstractProcess's modificationDate is not equal to the given one.
     */
    public AbstractProcessAssert hasModificationDate(Date modificationDate) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected <%s> modificationDate to be:\n  <%s>\n but was:\n  <%s>", actual, modificationDate,
                actual.getModificationDate());

        // check
        if (!actual.getModificationDate().equals(modificationDate)) {
            throw new AssertionError(errorMessage);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's parameters contains the given Parameter elements.
     * @param parameters the given elements that should be contained in actual AbstractProcess's parameters.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's parameters does not contain all given Parameter elements.
     */
    public AbstractProcessAssert hasParameters(Parameter... parameters) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // check that given Parameter varargs is not null.
        if (parameters == null)
            throw new AssertionError("Expecting parameters parameter not to be null.");

        // check with standard error message (see commented below to set your own message).
        Assertions.assertThat(actual.getParameters()).contains(parameters);

        // uncomment the 4 lines below if you want to build your own error message :
        // WritableAssertionInfo assertionInfo = new WritableAssertionInfo();
        // String errorMessage = "my error message";
        // assertionInfo.overridingErrorMessage(errorMessage);
        // Iterables.instance().assertContains(assertionInfo, actual.getTeamMates(), teamMates);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess has no parameters.
     * @return this assertion object.
     * @throws AssertionError if the actual AbstractProcess's parameters is not empty.
     */
    public AbstractProcessAssert hasNoParameters() {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected :\n  <%s>\nnot to have parameters but had :\n  <%s>", actual, actual.getParameters());

        // check
        if (!actual.getParameters().isEmpty())
            throw new AssertionError(errorMessage);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AbstractProcess's version is equal to the given one.
     * @param version the given version to compare the actual AbstractProcess's version to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AbstractProcess's version is not equal to the given one.
     */
    public AbstractProcessAssert hasVersion(String version) {
        // check that actual AbstractProcess we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nExpected <%s> version to be:\n  <%s>\n but was:\n  <%s>", actual, version, actual.getVersion());

        // check
        if (!actual.getVersion().equals(version)) {
            throw new AssertionError(errorMessage);
        }

        // return the current assertion for method chaining
        return this;
    }

}
