package com.hp.wpp.service.mock;

import com.hp.wpp.avatar.service.ServiceTokenGenerator;
import com.hp.wpp.avatar.service.config.AvatarServiceConfig;

/**
 * Mock ServiceTokenGenerator for integration tests
 */
public class MockServiceTokenGenerator extends ServiceTokenGenerator {

    public MockServiceTokenGenerator(AvatarServiceConfig avatarServiceConfig) {
        super(avatarServiceConfig);
    }

    @Override
    public void init() {
        // Skip the actual token generator initialization in tests
    }

    @Override
    public String getToken() {
        // Return a mock token for testing
        return "mock-bearer-token-for-integration-tests";
    }
}
