package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CandidateService candidateService;

    @Autowired
    public CustomAuthenticationProvider(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Candidate> candidate = candidateService.findByEmail(email);

        // So sánh trực tiếp mật khẩu (không mã hóa)
        if (candidate.isPresent() && password.equals(candidate.get().getPassword())) {
            return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
