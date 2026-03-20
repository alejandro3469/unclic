package mx.com.endtoend.domain.clients.dto;

public class ClientListDto {

    private Long id;

    private Long noClient;

    private String taxpayer;

    private String customerType;

    private String rfc;

    private String mail;

    private String name;

    private String fatherSurname;

    private String motherSurname;

    private String businessName;

    private String contact;

    private String phone;

    private String cell;
    private boolean isExternal;

    public boolean getIsExternal() {
        return isExternal;
    }

    public void setIsExternal(boolean external) {
        isExternal = external;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoClient() {
        return noClient;
    }

    public void setNoClient(Long noClient) {
        this.noClient = noClient;
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherSurname() {
        return fatherSurname;
    }

    public void setFatherSurname(String fatherSurname) {
        this.fatherSurname = fatherSurname;
    }

    public String getMotherSurname() {
        return motherSurname;
    }

    public void setMotherSurname(String motherSurname) {
        this.motherSurname = motherSurname;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public static class ClientDtoBuilder {

        public static ClientListDto build(long id, long noCliente, String taxPayer, String customerType, String rfc,
                                          String email, String name, String fatherSurname, String motherSurname, String businessName,
                                          String contact, String phone, String mobile) {
            ClientListDto clientListDto = new ClientListDto();


            clientListDto.setId(id);
            clientListDto.setNoClient(noCliente);
            clientListDto.setTaxpayer(taxPayer);

            if (customerType == null) {
                clientListDto.setCustomerType("");
            } else {
                clientListDto.setCustomerType(customerType);
            }
            if (rfc == null) {
                clientListDto.setRfc("");
            } else {
                clientListDto.setRfc(rfc);
            }
            if (email == null) {
                clientListDto.setMail("");
            } else {
                clientListDto.setMail(email);
            }
            if (name == null) {
                clientListDto.setName("");
            } else {
                clientListDto.setName(name);
            }
            if (fatherSurname == null) {
                clientListDto.setFatherSurname("");
            } else {
                clientListDto.setFatherSurname(fatherSurname);
            }
            if (motherSurname == null) {
                clientListDto.setMotherSurname("");
            } else {
                clientListDto.setMotherSurname(motherSurname);
            }
            if (businessName == null) {
                clientListDto.setBusinessName("");
            } else {
                clientListDto.setBusinessName(businessName);
            }
            if (contact == null) {
                clientListDto.setContact("");
            }
            clientListDto.setContact(contact);
            clientListDto.setPhone(phone);
            clientListDto.setCell(mobile);
            return clientListDto;
        }

        public static ClientListDto buildJDK(long noCliente, String taxPayer, String rfc, String name) {
            ClientListDto clientListDto = new ClientListDto();

            clientListDto.setNoClient(noCliente);
            clientListDto.setTaxpayer(taxPayer);
//			clientListDto.setCustomerType(customerType);
            clientListDto.setRfc(rfc);
//			clientListDto.setMail(email);
            clientListDto.setName(name);
//			clientListDto.setFatherSurname(fatherSurname);
//			clientListDto.setMotherSurname(motherSurname);
//			clientListDto.setBusinessName(businessName);
//			clientListDto.setContact(contact);
//			clientListDto.setPhone(phone);
//			clientListDto.setCell(mobile);

            return clientListDto;
        }
    }
}
