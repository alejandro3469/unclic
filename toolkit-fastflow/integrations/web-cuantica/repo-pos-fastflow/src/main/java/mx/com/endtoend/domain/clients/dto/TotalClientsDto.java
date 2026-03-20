package mx.com.endtoend.domain.clients.dto;

import java.util.List;

public class TotalClientsDto {
    private Integer total;
    private List<ClientListDto> clients;

    public TotalClientsDto() {
        super();
    }

    public TotalClientsDto(Integer total, List<ClientListDto> clients) {
        super();
        this.total = total;
        this.clients = clients;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ClientListDto> getClients() {
        return clients;
    }

    public void setClients(List<ClientListDto> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "TotalClientsDto{" +
                "total=" + total +
                ", clients=" + clients.size() +
                '}';
    }
}
