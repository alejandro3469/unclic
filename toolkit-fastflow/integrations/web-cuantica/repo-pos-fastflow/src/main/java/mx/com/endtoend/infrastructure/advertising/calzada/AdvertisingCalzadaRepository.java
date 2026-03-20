package mx.com.endtoend.infrastructure.advertising.calzada;


import mx.com.endtoend.infrastructure.advertising.common.factory.AdversitingRepositoryFactory;
import mx.com.endtoend.infrastructure.advertising.common.repository.BaseAdvertisingRepository;
import org.springframework.stereotype.Repository;
import mx.com.endtoend.infrastructure.advertising.calzada.repositories.SaleAdversitingCalzadaRepository;
import mx.com.endtoend.infrastructure.advertising.common.converters.AdvertisingConverter;

@Repository
public class AdvertisingCalzadaRepository extends BaseAdvertisingRepository {

	public AdvertisingCalzadaRepository(SaleAdversitingCalzadaRepository saleAdversitingRepository,
										AdvertisingConverter advertisingConverter) {
		super(AdversitingRepositoryFactory.class,
				saleAdversitingRepository,
				advertisingConverter);
	}

}
