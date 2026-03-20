package mx.com.endtoend.infrastructure.services.selia;

import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;

public interface SeliaServicePort {

	SeliaResponse generatRecharge(SeliaRequest seliaRequest);

}
