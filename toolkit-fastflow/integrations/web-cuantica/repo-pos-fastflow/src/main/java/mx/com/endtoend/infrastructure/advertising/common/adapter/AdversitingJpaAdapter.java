package mx.com.endtoend.infrastructure.advertising.common.adapter;

import mx.com.endtoend.infrastructure.advertising.common.factory.AdversitingRepositoryFactory;

public class AdversitingJpaAdapter extends BaseAdversitingJpaAdapter {


	public AdversitingJpaAdapter(AdversitingRepositoryFactory adversitingRepositoryFactory) {
		super(AdversitingJpaAdapter.class,
				adversitingRepositoryFactory);
	}

}
