package ar.utn.ba.ddsi.cliente_liviano.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int code = status != null ? Integer.parseInt(status.toString()) : 500;
        String mensaje;

        switch (code) {
            case 404 -> mensaje = "La página que buscás no existe!";
            case 403 -> mensaje = "No tenés permiso para acceder a esta página!";
            case 500 -> mensaje = "Ocurrió un error inesperado en el servidor!";
            default -> mensaje = "Algo salió mal. Intentá nuevamente!";
        }

        model.addAttribute("codigo", code);
        model.addAttribute("mensaje", mensaje);

        return "error/error"; // ✅ usa la misma vista para todos
    }
}
