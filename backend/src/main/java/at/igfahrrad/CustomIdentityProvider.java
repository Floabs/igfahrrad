package at.igfahrrad;

import java.util.Set;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomIdentityProvider {

    @Inject
    SecurityIdentity securityIdentity;

    public Set<String> getRoles() {
        return securityIdentity.getRoles();
    }
}

