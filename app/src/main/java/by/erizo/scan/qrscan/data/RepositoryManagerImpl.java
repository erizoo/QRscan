package by.erizo.scan.qrscan.data;

import javax.inject.Inject;

import by.erizo.scan.qrscan.data.network.ServiceNetwork;

public class RepositoryManagerImpl implements RepositoryManager {

    private ServiceNetwork serviceNetwork;


    @Inject
    RepositoryManagerImpl(ServiceNetwork serviceNetwork) {
        this.serviceNetwork = serviceNetwork;
    }

    @Override
    public ServiceNetwork getServiceNetwork() {
        return serviceNetwork;
    }
}
