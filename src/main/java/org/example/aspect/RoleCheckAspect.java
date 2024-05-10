package org.example.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.entity.User;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleCheckAspect {

    private final AuthService authService;

    @Autowired
    public RoleCheckAspect(AuthService authService) {
        this.authService = authService;
    }

    @Before("execution(* org.example.controller.OrderController.*(..))")
    public void checkUserRoleOrder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());

        if (!user.getRole().equals("client")) {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @Before("execution(* org.example.controller.ReviewController.*(..)) " +
            "&& !execution(* org.example.controller.ReviewController.getReview(..))")
    public void checkUserRoleReview() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());

        if (!user.getRole().equals("client")) {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @Before("execution(* org.example.controller.ProductController.addProduct(..)) " +
            "|| execution(* org.example.controller.ProductController.updateProduct(..)) + " +
            "|| execution(* org.example.controller.ProductController.deleteProduct(..))")
    public void checkUserRoleProduct() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());

        if (!user.getRole().equals("admin")) {
            throw new AccessDeniedException("Access is denied");
        }
    }
}

