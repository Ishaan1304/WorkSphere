package com.worksphere.WorkSphere.tenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource
        extends AbstractRoutingDataSource {    
    
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenant();
    }
}