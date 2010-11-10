package de.escidoc.admintool.service;

import gov.loc.www.zing.srw.SearchRetrieveRequestType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

import de.escidoc.core.client.exceptions.EscidocClientException;
import de.escidoc.core.client.exceptions.EscidocException;
import de.escidoc.core.client.exceptions.InternalClientException;
import de.escidoc.core.client.exceptions.TransportException;
import de.escidoc.core.client.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.client.interfaces.HandlerServiceInterface;
import de.escidoc.core.resources.Resource;
import de.escidoc.core.resources.common.TaskParam;

public abstract class AbstractEscidocService<T extends HandlerServiceInterface>
    implements ResourceService, LoginService {

    protected HandlerServiceInterface client;

    public AbstractEscidocService(final HandlerServiceInterface client) {
        Preconditions
            .checkNotNull(client, "Client can not be null: %s", client);
        this.client = client;
    }

    abstract T getClient();

    @Override
    public void loginWith(final String handle) throws InternalClientException {
        client.setHandle(handle);
    }

    @Override
    public Set<Resource> findAll() throws EscidocClientException {

        Collection<? extends Resource> resources;
        try {
            resources = findPublicOrReleasedResources();
        }
        catch (final EscidocException e) {
            if (e instanceof InvalidSearchQueryException) {
                resources = findPublicOrReleseadResourcesUsingOldFilter();
            }
            else {
                throw e;
            }
        }
        return Collections.unmodifiableSet(new HashSet<Resource>(resources));
    }

    protected TaskParam withEmptyTaskParam() {
        return new TaskParam();
    }

    protected SearchRetrieveRequestType withEmptyFilter() {
        return new SearchRetrieveRequestType();
    }

    abstract Collection<? extends Resource> findPublicOrReleseadResourcesUsingOldFilter()
        throws EscidocException, InternalClientException, TransportException,
        EscidocClientException;

    abstract Collection<? extends Resource> findPublicOrReleasedResources()
        throws EscidocException, InternalClientException, TransportException;

    public abstract Collection<? extends Resource> filterUsingInput(String query)
        throws EscidocException, InternalClientException, TransportException;

    protected SearchRetrieveRequestType userInputToFilter(final String query) {
        final SearchRetrieveRequestType filter =
            new SearchRetrieveRequestType();
        filter.setQuery(query);
        return filter;
    }
}