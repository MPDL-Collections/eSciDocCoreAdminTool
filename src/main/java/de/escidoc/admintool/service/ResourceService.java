package de.escidoc.admintool.service;

import java.util.Set;

import de.escidoc.core.client.exceptions.EscidocClientException;
import de.escidoc.core.client.exceptions.EscidocException;
import de.escidoc.core.client.exceptions.InternalClientException;
import de.escidoc.core.client.exceptions.TransportException;
import de.escidoc.core.resources.Resource;

public interface ResourceService extends EscidocService {

    void loginWith(String handle) throws InternalClientException;

    Set<Resource> findAll() throws EscidocClientException;

    Resource create(Resource resource) throws EscidocException,
        InternalClientException, TransportException;

}
