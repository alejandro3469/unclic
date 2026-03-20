package mx.com.endtoend.infrastructure.services.jde.clients.carredana.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.clients.dto.ClientListDto;
import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.dto.TotalClientsDto;
import mx.com.endtoend.genericCommonsFileds.utilities.DateUtil;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities.F0005;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.F0005FcarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.common.repository.ClientOracleRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0002;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0101;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0101Z2;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0101Z2Id;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0111;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0111Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0111Z1Id;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0115;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F01151;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F01151Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F01151Z1Id;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0115Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0115Z1Id;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0116;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F03012;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F03012Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F03012Z1Id;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0002FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0101FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0101Z2FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0111FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0111Z1FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F01151FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F01151Z1FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0115FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0115Z1FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0116FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F03012FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F03012Z1FCarRepository;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDirectionDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientMailDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;

@Service
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class ClientOracleCarredanaRepository implements ClientOracleRepository {

	private static final String REFERENCE_SM = "SMARTB";

	private final Logger LOG = LoggerFactory.getLogger(ClientOracleCarredanaRepository.class);

	@Autowired
	private F0101Z2FCarRepository f0101z2Repository;

	@Autowired
	private F01151Z1FCarRepository f01151z1Repository;

	@Autowired
	private F0115Z1FCarRepository f0115z1Repository;

	@Autowired
	private F03012Z1FCarRepository f03012z1Repository;

	@Autowired
	private F0002FCarRepository f0002Repository;

	@Autowired
	private F0101FCarRepository f0101Repository;

	@Autowired
	private F0116FCarRepository f0116Repository;

	@Autowired
	private F0115FCarRepository f0115Repository;

	@Autowired
	private F01151FCarRepository f01151Repository;

	@Autowired
	private F03012FCarRepository f03012Repository;

	@Autowired
	private F0111FCarRepository f0111Repository;

	@Autowired
	private F0111Z1FCarRepository f0111z1Repository;

	@Autowired
	private F0005FcarRepository f0005Repository;

	@Autowired
	private CompanyRepository companyRepository;
	
	private Long currentJulianDay = getCurrentJulianDate();

	@Override
	public TotalClientsDto getClientList(FiltersClientDto filtersClientDto, String idOperation) {
		LOG.info(String.format("%s INIT getListClientOracle()", idOperation));
		LOG.info(String.format("%s PARAMS: [ filtersClientDto: %s ]", idOperation, filtersClientDto.toString()));

		List<ClientDto> clientDtoList = new ArrayList<>();
		List<ClientListDto> clientListDtoList = new ArrayList<>();
		TotalClientsDto totalClientsDto = new TotalClientsDto();

		LOG.info("CURRENT-DAY: " + currentJulianDay);

		List<F0101> clientListOracle = f0101Repository.findByParams(filtersClientDto);

		LOG.info(String.format("%s SET PAGE AN SIZE", idOperation));
		PagedListHolder<F0101> pagedListHolder = new PagedListHolder<>(clientListOracle);
		pagedListHolder.setPageSize(filtersClientDto.getRow());
		pagedListHolder.setPage(filtersClientDto.getPage() - 1);

		for (F0101 clientOracle : pagedListHolder.getPageList()) {
			ClientDto clientDto = getClientDto(idOperation, clientOracle);
			clientDtoList.add(clientDto);
		}

		for (ClientDto clientDto : clientDtoList) {
			LOG.info("DATA-> " + clientDto.toString());
			ClientListDto clientListDto = ClientListDto.ClientDtoBuilder.build(0L, clientDto.getNoClient(),
					clientDto.getTaxpayer(), clientDto.getCustomerType(), clientDto.getRfc(),
					clientDto.getMailList() != null ? clientDto.getMailList().get(0).getMail() : "",
					clientDto.getName(), clientDto.getFatherSurname(), clientDto.getMotherSurname(),
					clientDto.getBusinessName(), clientDto.getContact(), clientDto.getPhone(), clientDto.getCell());
			clientListDto.setIsExternal(true);
			clientListDtoList.add(clientListDto);
		}
		totalClientsDto.setClients(clientListDtoList);
		totalClientsDto.setTotal(pagedListHolder.getPageCount());
		return totalClientsDto;
	}

	private ClientDto getClientDto(String idOperation, F0101 clientOracle) {
		ClientDto clientDto = new ClientDto();
		ClientDirectionDto clientDirectionDto = new ClientDirectionDto();
		List<ShippingAddressDto> shippingAddressDtoList = new ArrayList<>();
		ShippingAddressDto shippingAddressDto = new ShippingAddressDto();

		clientDto.setNoClient(clientOracle.getNoClient());
		clientDto.setRfc(clientOracle.getRfc().trim());
		clientDto.setTaxpayer(clientOracle.getTaxpayer());
		String name = clientOracle.getName().trim();

		F0116 f0116 = f0116Repository.findByIdNoClient(clientOracle.getNoClient());

		if (f0116.getName1() == null || f0116.getName1().isEmpty()) {

			if (clientOracle.getTaxpayer() == "F") {

				clientDto.setName(name);
			} else if (clientOracle.getTaxpayer() == "M") {

				clientDto.setBusinessName(name);
			} else if (clientOracle.getRfc().trim() == "XAXX010101000" || clientOracle.getRfc().trim().length() == 13) {

				clientDto.setName(name);
				clientDto.setTaxpayer("F");

			} else if (clientOracle.getRfc().trim().length() == 12) {

				clientDto.setBusinessName(name);
				clientDto.setTaxpayer("M");
			} else {

				clientDto.setBusinessName(name);
			}

		} else {

			if (clientOracle.getTaxpayer() == "F") {

				clientDto.setName(name + f0116.getName1().trim());

			} else if (clientOracle.getRfc().trim() == "M") {

				clientDto.setBusinessName(name + f0116.getName1().trim());

			} else if (clientOracle.getRfc().trim() == "XAXX010101000" || clientOracle.getRfc().trim().length() == 13) {

				clientDto.setName(name + f0116.getName1().trim());
				clientDto.setTaxpayer("F");

			} else if (clientOracle.getRfc().trim().length() == 12) {

				clientDto.setBusinessName(name + f0116.getName1().trim());
				clientDto.setTaxpayer("M");

			} else {

				clientDto.setBusinessName(name + f0116.getName1().trim());
			}

		}

		clientDirectionDto.setCp(f0116.getCp().trim());
		clientDirectionDto.setCity(f0116.getCity().trim());
		String noOuNoIn = f0116.getNoOuNoIn().trim();

		if (noOuNoIn.contains("/")) {

			String[] parts = noOuNoIn.split("/");
			clientDirectionDto.setNoOutdoor(parts[0]);
			shippingAddressDto.setNoOutdoor(parts[0]);
			if (parts.length < 0) {
				clientDirectionDto.setNoInterior(parts[1]);
				shippingAddressDto.setNoInterior(parts[1]);
			}

		} else if (noOuNoIn.contains("-")) {

			String[] parts = noOuNoIn.split("-");
			clientDirectionDto.setNoOutdoor(parts[0]);
			shippingAddressDto.setNoOutdoor(parts[0]);
			if (parts.length < 0) {
				clientDirectionDto.setNoInterior(parts[1]);
				shippingAddressDto.setNoInterior(parts[1]);
			}

		} else {

			clientDirectionDto.setNoOutdoor(noOuNoIn);
			shippingAddressDto.setNoOutdoor(noOuNoIn);
		}

		clientDirectionDto.setStreet(f0116.getStreet().trim());
		clientDirectionDto.setColony(f0116.getColony().trim());
		clientDirectionDto.setStateCode(f0116.getStateCode());

		shippingAddressDto.setCp(f0116.getCp().trim());
		shippingAddressDto.setCity(f0116.getCity().trim());

		shippingAddressDto.setStreet(f0116.getStreet().trim());
		shippingAddressDto.setColony(f0116.getColony().trim());
		shippingAddressDto.setStateCode(f0116.getStateCode());

		if (f0116.getName1() == null || f0116.getName1().trim().isEmpty()) {

			shippingAddressDto.setUvicationName(name);

		} else {

			shippingAddressDto.setUvicationName(name + f0116.getName1().trim());

		}

		F0115 phone = f0115Repository.findFirstByIdNoClientAndIdWprck7(clientOracle.getNoClient(), "1");

		if (phone != null) {
			clientDto.setPhone(phone.getNumber() + phone.getNumber1());

			if (clientDto.getPhone().contains(".")) {
				clientDto.setPhone("0");
			}
		}

		F0115 cell = f0115Repository.findFirstByIdNoClientAndIdWprck7(clientOracle.getNoClient(), "2");

		if (cell != null) {
			clientDto.setCell(cell.getNumber() + cell.getNumber1());

			if (clientDto.getCell().contains(".")) {
				clientDto.setCell("0");
			}
		}

		F03012 f03012 = f03012Repository.findByIdNoClient(clientOracle.getNoClient());

		if (f03012 != null && f03012.getIva().contains("IV")) {
			String[] parts = f03012.getIva().split("IV");
			clientDto.setIva(parts[1]);
		}

		F0111 f0111 = f0111Repository.findFirstByIdNoClient(clientOracle.getNoClient());

		if (f0111 != null) {

			clientDto.setContact(f0111.getContact().trim());
		}

		clientDto.setDirection(clientDirectionDto);

		shippingAddressDtoList.add(shippingAddressDto);

		clientDto.setShippingAddressList(shippingAddressDtoList);

		F01151 f01151 = f01151Repository.findFirstByIdNoClientAndIdEarck7(clientOracle.getNoClient(), "1");

		if (f01151 != null) {
			LOG.info(String.format("%s USER HAVE EMAIL", idOperation));
			List<ClientMailDto> maiClientMailDtoList = new ArrayList<>();

			ClientMailDto clientMailDto = new ClientMailDto();
			clientMailDto.setMail(f01151.getMail().trim());
			maiClientMailDtoList.add(clientMailDto);
			clientDto.setMailList(maiClientMailDtoList);
		}
		return clientDto;
	}

	@Override
	public ClientDto getClientByClientNumber(Long clientNumber, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getClientByClientNumber()", idOperation));
			LOG.info(String.format("%s PARAMS: [ clientNumber: %s ]", idOperation, clientNumber.toString()));
			Optional<F0101> f0101 = f0101Repository.getByAn8(clientNumber);
			ClientDto clientDto = null;
			if (f0101.isPresent()) {
				clientDto = getClientDto(idOperation, f0101.get());
			}
			return clientDto;
		} catch (Exception e) {
			LOG.info(String.format("%s ERROR IN SEARCH CLIENT BY CLIENT-NUMBER", idOperation));
			throw new GlobalError();
		}
	}

	/**
	 * Elimina el cliente de las tablas batch de JDE y MySql
	 *
	 * @param client cliente
	 */

	private void deleteClientFromBatch(ClientDto client) {
		LOG.info("METHOD: deleteClientFromBatch() --PARAM: client=" + client);
		List<F0101Z2> f0101Z2List = f0101z2Repository.findRecordByNoClient(client.getNoClient().toString());
		for (F0101Z2 f0101Z2 : f0101Z2List) {
			f0101z2Repository.delete(f0101Z2);
		}
		List<F0111Z1> contactsInBatch = f0111z1Repository.findByNoClient(client.getNoClient());
		for (F0111Z1 contactInBatch : contactsInBatch) {

			f0111z1Repository.delete(contactInBatch);
		}
		List<F01151Z1> emailsInBatch = f01151z1Repository.findByNoClient(client.getNoClient().toString());
		for (F01151Z1 mailInBatch : emailsInBatch) {

			f01151z1Repository.delete(mailInBatch);
		}
		List<F0115Z1> phonesInBatch = f0115z1Repository.findByNoClient(client.getNoClient().toString());
		for (F0115Z1 phoneInBatch : phonesInBatch) {

			f0115z1Repository.delete(phoneInBatch);
		}
	}

	/**
	 * Método para la modificación de un cliente
	 *
	 * @param clientDto   cliente
	 * @param idOperation identificador de la operación
	 * @return boolean indicador para resultado de la operación, true proceso
	 *         completo y false proceso incompleto
	 */

	public boolean updateClient(ClientDto clientDto, String companyCode, String idOperation) {
		LOG.info(idOperation + "START THE MODIFICATION OF THE ORACLE CLIENT");

		boolean existsClientInJDE = existsAddressBookJDE(clientDto);

		deleteClientFromBatch(clientDto);

		if (existsClientInJDE) {
			existsClientInJDE = createClient(clientDto, "C", companyCode, idOperation);
		} else {
			existsClientInJDE = createClient(clientDto, "A", companyCode, idOperation);
		}

		return existsClientInJDE;
	}

	/**
	 * Verifica si existe en la tabla de JDE
	 *
	 * @param client cliente
	 * @return true si existe/false no existe
	 */
	private boolean existsAddressBookJDE(ClientDto client) {
		LOG.info("METHOD: existsClientInJDE() --PARAM: client=" + client);
		List<F0101> f0101List = f0101Repository.getClientListByAn8(client.getNoClient());
		return f0101List.size() != 0;
	}

	/**
	 * Obtiene el número para asignación de clientes nuevos
	 *
	 * @param idOperation identificador de traza
	 * @return Long número de cliente
	 */
	@Override
	public Long getClientNumber(String idOperation) {

		F0002 noClient = f0002Repository.NoClient("01");

		Long noClientOracle = Long.parseLong(noClient.getNnn001());

		Long lastNoClient = noClientOracle + 1;
		noClient.setNnn001(lastNoClient.toString());
		f0002Repository.save(noClient);

		return noClientOracle;
	}

	/**
	 * Obtiene número de referencia para asignación de clientes nuevos
	 *
	 * @param idOperation identificador de traza
	 * @return String número de referencia
	 */
	@Override
	public String getClientReferenceNumber(String idOperation) {

		F0002 szedbt = f0002Repository.NoClient("47");

		Long szedbtLong = Long.parseLong(szedbt.getNnn001());
		Long suma = szedbtLong + 1;
		szedbt.setNnn001(suma.toString());
		f0002Repository.save(szedbt);

		return szedbtLong.toString();
	}

	/**
	 * Método para la creación de un cliente
	 *
	 * @param clientDto   objeto con la información del cliente a almacenar
	 * @param tnac        indicador para creación de nuevo registro
	 * @param idOperation identificador de traza
	 * @return boolean indicador para resultado de la operación, true proceso
	 *         completo y false proceso incompleto
	 */
	@Override
	public boolean createClient(ClientDto clientDto, String tnac, String companyCode, String idOperation) {

		try {

			LOG.info(String.format("%s INIT createClient()", idOperation));
			LOG.info(String.format("%s PARAMS: [ clientDto: %s , tnac: %s , companyCode: %s]", idOperation,
					clientDto.toString(), tnac, companyCode));

			CompanyEntity companyEntity = companyRepository.findByCode(CompanyCodes.valueOf(companyCode));
			F0005 f0005 = f0005Repository.findByDrsyAndDrrtAndDrsphdDrdl02("01", "04", "1",
					companyEntity.getCompanyNumber());

			String name = clientDto.getName() + " " + clientDto.getFatherSurname() + " " + clientDto.getMotherSurname();
			String businessName = "";
			String businessName1 = "";

			if (clientDto.getName() == null || clientDto.getName().isEmpty()) {
				if (clientDto.getBusinessName().length() >= 40) {
					businessName = clientDto.getBusinessName().substring(0, 40);
					businessName1 = clientDto.getBusinessName().substring(40);
				} else {
					businessName = clientDto.getBusinessName();
					businessName1 = clientDto.getBusinessName();
				}
			} else {
				if (name.length() >= 40) {
					businessName = name.substring(0, 40);
					businessName1 = name.substring(40);
				} else {
					businessName = name;
					businessName = name;
				}
			}

			if (f0005 != null) {

				F0101Z2 f0101z2 = new F0101Z2();
				F0101Z2Id f0101z2Id = new F0101Z2Id();

				f0101z2.setBranchCode("           3");

				f0101z2Id.setSzedus(REFERENCE_SM);
				f0101z2Id.setSzedtn("1");
				f0101z2Id.setSzedln("0");
				f0101z2Id.setSzedbt(clientDto.getNnn001());
				f0101z2.setId(f0101z2Id);
				f0101z2.setSztytn("JDEAB");
				f0101z2.setSzeddt("0");
				f0101z2.setSzedsp(" ");
				f0101z2.setSztnac(tnac);
				f0101z2.setNoClient(clientDto.getNoClient().toString());
				f0101z2.setRfc(clientDto.getRfc());

				f0101z2.setName(businessName);
				f0101z2.setCopyName(businessName);
				f0101z2.setName1(businessName1);

				f0101z2.setSzat1("C");
				f0101z2.setTaxPayer(clientDto.getTaxpayer());
				f0101z2.setSzat2("N");
				f0101z2.setSzatr("Y");
				f0101z2.setSzat5("N");
				f0101z2.setSzatp("N");
				f0101z2.setSzate("N");
				f0101z2.setSzeftb("0");
				f0101z2.setSzan81("0");
				f0101z2.setSzan82("0");
				f0101z2.setSzan83("0");
				f0101z2.setSzan84("0");
				f0101z2.setSzan85("0");
				f0101z2.setSzan86("0");
				f0101z2.setSzac10("1");
				f0101z2.setTaxRegime(clientDto.getTaxRegime());
				f0101z2.setSzpti("0");
				f0101z2.setSzpdi("0");
				f0101z2.setSzurat("0");
				f0101z2.setSzurat("0");
				f0101z2.setSzurab("0");

				f0101z2.setStreet(clientDto.getDirection().getStreet());
				String noOuNoIn = clientDto.getDirection().getNoOutdoor() + "/"
						+ clientDto.getDirection().getNoInterior();
				f0101z2.setNoOuNoIn(noOuNoIn);
				f0101z2.setColony(clientDto.getDirection().getColony());
				f0101z2.setCp(clientDto.getDirection().getCp().toString());
				f0101z2.setDelegationCode(clientDto.getDirection().getDelegationCode());
				f0101z2.setSzctr("MX");
				f0101z2.setStateCode(clientDto.getDirection().getStateCode());
				f0101z2.setCity(clientDto.getDirection().getCity());

				f0101z2.setSztorg(REFERENCE_SM);
				f0101z2.setSzuser(REFERENCE_SM);
				f0101z2.setSzpid(REFERENCE_SM);
				f0101z2.setSzjobn(" ");
				f0101z2.setSzupmj(String.valueOf(currentJulianDay));
				f0101z2.setSztday("0");
				f0101z2.setSzupmt("0");

				f0101z2.setCompany(f0005.getId().getDrky());

				f0101z2Repository.save(f0101z2);
			}

			F0111Z1 f0111z1 = new F0111Z1();
			F0111Z1Id f0111z1Id = new F0111Z1Id();

			f0111z1Id.setBwedus(REFERENCE_SM);
			f0111z1Id.setBwedbt(clientDto.getNnn001());
			f0111z1Id.setBwedtn("1");
			f0111z1Id.setBwedln("0");
			f0111z1.setBwedct(" ");
			f0111z1.setId(f0111z1Id);
			f0111z1.setBwtytn(" ");
			LocalDate todaysDate = LocalDate.now();
			f0111z1.setBweddt(DateUtil.transformDateJuliana(todaysDate.toString()));
			f0111z1.setBwdrin(" ");
			f0111z1.setBweddl("0");
			f0111z1.setBwedsp(" ");
			f0111z1.setBwtnac(tnac);
			f0111z1.setNoClient(clientDto.getNoClient());
			f0111z1.setBwidln("0");
			f0111z1.setBwdss5("0");
			f0111z1.setBwnick(clientDto.getContact());
			f0111z1.setBwmlnm(businessName);
			f0111z1.setBwalph(businessName);
			f0111z1.setBwtyc(" ");
			f0111z1.setBwuser(REFERENCE_SM);
			f0111z1.setBwjobn(REFERENCE_SM);
			f0111z1.setBwchproc(" ");

			f0111z1Repository.save(f0111z1);

			if (clientDto.getPhone() != null) {
				if (!clientDto.getPhone().isEmpty()) {

					F0115Z1 f0115z1 = new F0115Z1();
					F0115Z1Id f0115z1Id = new F0115Z1Id();

					f0115z1Id.setPiedus(REFERENCE_SM);
					f0115z1Id.setPiedbt(clientDto.getNnn001());
					f0115z1Id.setPiedtn("1");
					f0115z1Id.setPiedln("0");
					f0115z1Id.setPiedtl("1");
					f0115z1.setId(f0115z1Id);
					f0115z1.setPiedsp(" ");
					f0115z1.setPitnac(tnac);
					f0115z1.setNoClient(clientDto.getNoClient().toString());
					f0115z1.setPiidln("1");
					f0115z1.setPirck7("0");
					f0115z1.setCellHouse("CASA");
					f0115z1.setNumber(clientDto.getPhone());
					f0115z1.setPiuser(REFERENCE_SM);
					f0115z1.setPipid(REFERENCE_SM);
					f0115z1.setPiupmj(String.valueOf(currentJulianDay));
					f0115z1.setPijobn(" ");
					f0115z1.setPiupmt("0");
					f0115z1.setPicfno1("0");
					f0115z1.setPiar1(" ");
					f0115z1.setPiedct(" ");
					f0115z1.setPitytn(" ");
					f0115z1.setPiedft(" ");
					f0115z1.setPieddt("0");
					f0115z1.setPidrin(" ");

					f0115z1Repository.save(f0115z1);

				}
			}

			if (clientDto.getCell() != null) {
				if (!clientDto.getCell().isEmpty()) {

					F0115Z1 f0115z1 = new F0115Z1();
					F0115Z1Id f0115z1Id = new F0115Z1Id();

					f0115z1Id.setPiedus(REFERENCE_SM);
					f0115z1Id.setPiedbt(clientDto.getNnn001());
					f0115z1Id.setPiedtn("1");
					f0115z1Id.setPiedln("0");
					f0115z1Id.setPiedtl("2");
					f0115z1.setId(f0115z1Id);
					f0115z1.setPiedsp(" ");
					f0115z1.setPitnac(tnac);
					f0115z1.setNoClient(clientDto.getNoClient().toString());
					f0115z1.setPiidln("2");
					f0115z1.setPirck7("0");
					f0115z1.setCellHouse("CEL");
					f0115z1.setNumber(clientDto.getCell());
					f0115z1.setPiuser(REFERENCE_SM);
					f0115z1.setPipid(REFERENCE_SM);
					f0115z1.setPiupmj(String.valueOf(currentJulianDay));
					f0115z1.setPijobn(" ");
					f0115z1.setPiupmt("0");
					f0115z1.setPicfno1("0");
					f0115z1.setPiar1(" ");
					f0115z1.setPiedct(" ");
					f0115z1.setPitytn(" ");
					f0115z1.setPiedft(" ");
					f0115z1.setPieddt("0");
					f0115z1.setPidrin(" ");
					f0115z1Repository.save(f0115z1);
				}
			}

			List<ClientMailDto> mailList = clientDto.getMailList();
			String email = "";
			if (mailList.size() != 0) {
				email = mailList.get(0).getMail();
			}
			F01151Z1 f01151z1 = new F01151Z1();
			F01151Z1Id f01151z1Id = new F01151Z1Id();

			f01151z1Id.setEbedus(REFERENCE_SM);
			f01151z1Id.setEbedbt(clientDto.getNnn001());
			f01151z1Id.setEbedtn("1");
			f01151z1Id.setEbedln("1000");
			f01151z1.setId(f01151z1Id);
			f01151z1.setEbtytn("JDECI");
			f01151z1.setEbeddt("0");
			f01151z1.setEbdrin(" ");
			f01151z1.setEbedsp(" ");
			f01151z1.setEbtnac("A");
			f01151z1.setNoClient(clientDto.getNoClient().toString());
			f01151z1.setEbidln("0");
			f01151z1.setEbrck7("0");
			f01151z1.setEbetp("E");
			f01151z1.setEmail(email);
			f01151z1.setEbuser(REFERENCE_SM);
			f01151z1.setEbpid("R011110Z");
			f01151z1.setEbupmj(String.valueOf(currentJulianDay));
			f01151z1.setEbjobn("FCVJDEENT");
			f01151z1.setEbtday("0");
			f01151z1.setEbupmt("0");
			f01151z1.setEbehier("0");
			f01151z1.setEbedct(" ");
			f01151z1.setEbedft(" ");

			f01151z1Repository.save(f01151z1);

			Optional<F03012Z1> exists = f03012z1Repository.findByNoClient(clientDto.getNoClient().toString());

			if (exists.isEmpty()) {

				F03012Z1 f03012z1 = new F03012Z1();
				F03012Z1Id f03012z1Id = new F03012Z1Id();

				f03012z1Id.setVoedus(REFERENCE_SM);
				f03012z1Id.setVoedbt(clientDto.getNnn001());
				f03012z1Id.setVoedtn("1");
				f03012z1Id.setVoedln("0");
				f03012z1.setId(f03012z1Id);
				f03012z1.setVoeddt("0");
				f03012z1.setVoeddl("0");
				f03012z1.setVoedsp(" ");
				f03012z1.setVotnac(tnac);
				f03012z1.setNoClient(clientDto.getNoClient().toString());
				f03012z1.setVoco("00000");
				f03012z1.setVoarc("CNAC");
				f03012z1.setVodcar("0");
				f03012z1.setVocrcd("MXN");
				f03012z1.setIva("IV16");
				f03012z1.setVoexr1("V");
				f03012z1.setVoacl("0");
				f03012z1.setVohdar("N");
				f03012z1.setVotrar("C02");
				f03012z1.setVostto("C");
				f03012z1.setVoryin("#");
				f03012z1.setVostmt("Y");
				f03012z1.setVoarpy("0");
				f03012z1.setVoatcs("Y");
				f03012z1.setVosito("C");
				f03012z1.setVosqnl("6");
				f03012z1.setVoalgm("U");
				f03012z1.setVobo("B");
				f03012z1.setVodlc("0");
				f03012z1.setVodnlt("N");
				f03012z1.setVorvdj("0");
				f03012z1.setVodso("0");
				f03012z1.setVodlqt("0");
				f03012z1.setVodlqj("0");
				f03012z1.setVocoll("N");
				f03012z1.setVonbr1("0");
				f03012z1.setVonbr2("0");
				f03012z1.setVonbr3("0");
				f03012z1.setVonbcl("0");
				f03012z1.setVoafc("N");
				f03012z1.setVofd("0");
				f03012z1.setVofp("0");
				f03012z1.setVocfce("N");
				f03012z1.setVodt1j("0");
				f03012z1.setVodfij("0");
				f03012z1.setVodlij("0");
				f03012z1.setVoabc1("C");
				f03012z1.setVoabc2("C");
				f03012z1.setVoabc3("C");
				f03012z1.setVofndj("0");
				f03012z1.setVodlp("0");
				f03012z1.setVodnbj("0");
				f03012z1.setVotwdj("0");
				f03012z1.setVoavd("0");
				f03012z1.setVocrca("MXN");
				f03012z1.setVopopn("POSRE");
				f03012z1.setVoan8r("1");
				f03012z1.setVobadt("X");
				f03012z1.setVoexhd("Y");
				f03012z1.setVoaft("N");
				f03012z1.setVoapts("Y");
				f03012z1.setVosbal("Y");
				f03012z1.setVoback("Y");
				f03012z1.setVoporq("N");
				f03012z1.setVoprio("0");
				f03012z1.setVoarto("C");
				f03012z1.setVoinvc("1");
				f03012z1.setVoicon("N");
				f03012z1.setVoblfr("D");
				f03012z1.setVoplst("N");
				f03012z1.setVomord("N");
				f03012z1.setVoedpm("P");
				f03012z1.setVoedf1("Y");
				f03012z1.setVosi01("Y");
				f03012z1.setVosi02("N");
				f03012z1.setVoasn("OFERTAS");
				f03012z1.setVodspa("N");
				f03012z1.setVoac10("1");
				f03012z1.setVocfdf("1");
				f03012z1.setVouser(REFERENCE_SM);
				f03012z1.setVopid(REFERENCE_SM);
				f03012z1.setVojobn("P0111Z1");
				f03012z1.setVoupmt("0");
				f03012z1.setVoupmj(String.valueOf(currentJulianDay));
				f03012z1.setVobyal("1");
				f03012z1.setVodtee(new Date());

				f03012z1Repository.save(f03012z1);
			}

			return true;

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN SAVE DATA, EXCEPTION: %s ", idOperation, e.getMessage()));
			return false;

		}
	}
	
	@SuppressWarnings({ "removal" })
	public Long getCurrentJulianDate() {
		Long julianDate = 0L;
		StringBuilder sb = new StringBuilder();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTimeInMillis(System.currentTimeMillis());
		String date = sb.append("1").append(Integer.toString(currentCalendar.get(Calendar.YEAR)).substring(2, 4))
				.append(String.format("%03d", currentCalendar.get(Calendar.DAY_OF_YEAR))).toString();
		try {
			julianDate = new Long(date);
			return julianDate;
		} catch (NumberFormatException e) {
			LOG.error("METHOD: getCurrentJulianDate()");
			LOG.error("ERROR AL PARSEAR UN STRING A INTEGER", e.getMessage());
			return null;
		}
	}
}
