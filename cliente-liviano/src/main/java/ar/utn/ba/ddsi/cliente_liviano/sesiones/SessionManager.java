package ar.utn.ba.ddsi.cliente_liviano.sesiones;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionManager {

    public void storeTokens(HttpSession session, AuthResponse tokens) {
        session.setAttribute("accessToken", tokens.getAccessToken());
        session.setAttribute("refreshToken", tokens.getRefreshToken());
        session.setAttribute("roles", tokens.getRoles());
    }

    public String getAccessToken(HttpSession session) {
        return (String) session.getAttribute("accessToken");
    }

    public String getRefreshToken(HttpSession session) {
        return (String) session.getAttribute("refreshToken");
    }
}