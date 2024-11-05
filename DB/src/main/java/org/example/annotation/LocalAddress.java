package org.example.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.annotation.validator.LocalAddressValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LocalAddressValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalAddress {
    String message() default "병원 주소 형식과 알맞지 않습니다.";
    String regexp() default "^(서울특별시|세종특별자치시|(부산|인천|광주|대전|울산|대구)광역시|[가-힣]+도) ([가-힣]+(시|군|구)) ([가-힣]))$";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
