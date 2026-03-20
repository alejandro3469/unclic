package mx.com.endtoend.infrastructure.services.jde.clients.common.service;

import mx.com.endtoend.infrastructure.services.jde.clients.common.factory.ClientOracleFactory;
import mx.com.endtoend.infrastructure.services.jde.clients.common.repository.ClientOracleRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.dto.TotalClientsDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;


@Service
public class JdeClientService implements ClientOracleServicePort {

    private final Logger LOG = LoggerFactory.getLogger(JdeClientService.class);
    @Autowired
    private ClientOracleFactory clientOracleFactory;

    /**
     * Método que recuepra el repositorio de un clinete por código de compañía para
     * la obtención de una lista de clientes.
     *
     * @param filtersClientDto objeto con parámetros de búsqueda
     * @param companyCode      código de compañia
     * @param idOperation      identificador de traza
     * @return ResponseModel objeto que contiene la información recuperada, en caso
     * de ser exitoso, el código de respuesta será con un valor igual a 100
     */
    @Override
    public ResponseModel getListClientOracle(FiltersClientDto filtersClientDto, String companyCode,
                                             String idOperation) {
        LOG.info(String.format("%s INIT getListClientOracle()", idOperation));
        LOG.info(String.format("%s PARAMS: [ filtersClientDto: %s , companyCode: %s ] ", idOperation,
                filtersClientDto.toString(), companyCode));
        ClientOracleRepository clientRepository = clientOracleFactory.getClientRepository(companyCode);
        if (clientRepository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        TotalClientsDto clientDtoList = clientRepository.getClientList(filtersClientDto, idOperation);
        return new ResponseModel(clientDtoList);
    }

    /**
     * Método que recupera el repositorio de un cliente por código de compañía para la obtención del detalle de un clinete
     *
     * @param clientNumber número de cliente de JDE
     * @param companyCode  código de compañía
     * @param idOperation  identificador de traza de operación
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @Override
    public ResponseModel getClientByClientNumber(Long clientNumber, String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getClientByClientNumber()", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientNumber: %s , companyCode: %s ] ", idOperation,
                clientNumber.toString(), companyCode));
        ClientOracleRepository clientRepository = clientOracleFactory.getClientRepository(companyCode);
        if (clientRepository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ClientDto clientDto = clientRepository.getClientByClientNumber(clientNumber, idOperation);
        return new ResponseModel(clientDto);
    }

    /**
     * Método que recuepra el repositorio de un clinete por código de compañía para
     * la obtención de un numero consecutivo.
     *
     * @param companyCode código de compañia
     * @param idOperation identificador de traza
     * @return ResponseModel objeto que contiene la información recuperada, en caso
     * de ser exitoso, el código de respuesta será con un valor igual a 100
     */
    @Override
    public ResponseModel getClientNumber(String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT getClientNumber()", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s ] ", idOperation, companyCode));

        ClientOracleRepository clientRepository = clientOracleFactory.getClientRepository(companyCode);

        if (clientRepository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        Long clientNumber = clientRepository.getClientNumber(idOperation);

        return new ResponseModel(clientNumber);
    }

    /**
     * Método que recuepra el repositorio de un clinete por código de compañía para
     * la obtención de un numero de referecnia consecutivo.
     *
     * @param companyCode código de compañia
     * @param idOperation identificador de traza
     * @return ResponseModel objeto que contiene la información recuperada, en caso
     * de ser exitoso, el código de respuesta será con un valor igual a 100
     */
    @Override
    public ResponseModel getClientReferenceNumber(String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT getClientReferenceNumber()", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s ] ", idOperation, companyCode));

        ClientOracleRepository clientRepository = clientOracleFactory.getClientRepository(companyCode);

        if (clientRepository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        String clientReferenceNumber = clientRepository.getClientReferenceNumber(idOperation);

        return new ResponseModel(clientReferenceNumber);

    }

    /**
     * Método que recuepra el repositorio de un clinete por código de compañía para
     * la creación de un cliente
     *
     * @param clientDto   objeto con la información del clinete a almacenar
     * @param companyCode código de compañia
     * @param tnac        identificador para la creación o actualizacion de
     *                    registros, "A" y "C" respectivamente
     * @return ResponseModel objeto que contiene la información recuperada, en caso
     * de ser exitoso, el código de respuesta será con un valor igual a 100
     */
    @Override
    public ResponseModel createClient(ClientDto clientDto, String companyCode, String tnac, String idOperation) {

        LOG.info(String.format("%s INIT createClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s , tnac: %s ] ", idOperation,
                clientDto.toString(), companyCode, tnac));

        ClientOracleRepository clientRepository = clientOracleFactory.getClientRepository(companyCode);

        if (clientRepository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        boolean createOk = clientRepository.createClient(clientDto, tnac, companyCode, idOperation);

        return new ResponseModel(createOk);
    }

    /**
     * Método que recuepra el repositorio de un clinete por código de compañía para
     * la modificacion de un cliente
     *
     * @param clientDto   objeto con la información del clinete a almacenar
     * @param companyCode código de compañia
     * @return ResponseModel objeto que contiene la información recuperada, en caso
     * de ser exitoso, el código de respuesta será con un valor igual a 100
     */
    @Override
    public ResponseModel updateClient(ClientDto clientDto, String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT updateClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s] ", idOperation, clientDto.toString(),
                companyCode));

        ClientOracleRepository clientRepository = clientOracleFactory.getClientRepository(companyCode);

        if (clientRepository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        boolean createOk = clientRepository.updateClient(clientDto, companyCode, idOperation);

        return new ResponseModel(createOk);
    }

}
