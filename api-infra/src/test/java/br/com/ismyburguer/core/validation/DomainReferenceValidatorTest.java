package br.com.ismyburguer.core.validation;
import br.com.ismyburguer.core.adapter.ExistsByIdUseCase;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainReferenceValidatorTest {

    @Mock
    private ExistsByIdUseCase<?> existsByIdUseCase;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private DomainReferenceValidator validator;

    @BeforeEach
    void setup() {
        when(applicationContext.getBean(any(Class.class))).thenReturn(existsByIdUseCase);
    }

    @Test
    void validate_DeveLancarConstraintViolationException_QuandoEntityIdNaoExistir() {
        // Arrange
        UUID entityId = UUID.randomUUID();
        Class<? extends ExistsByIdUseCase> useCaseClass = ExistsByIdUseCase.class;
        String campo = "Entidade";

        when(existsByIdUseCase.existsById(entityId)).thenReturn(false);

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> validator.validate(useCaseClass, campo, entityId));

        // Verify
        verify(applicationContext, times(1)).getBean(useCaseClass);
        verify(existsByIdUseCase, times(1)).existsById(entityId);
    }

    @Test
    void validate_NaoDeveLancarConstraintViolationException_QuandoEntityIdExistir() {
        // Arrange
        UUID entityId = UUID.randomUUID();
        Class<? extends ExistsByIdUseCase> useCaseClass = ExistsByIdUseCase.class;
        String campo = "Entidade";

        when(existsByIdUseCase.existsById(entityId)).thenReturn(true);

        // Act + Assert
        validator.validate(useCaseClass, campo, entityId);

        // Verify
        verify(applicationContext, times(1)).getBean(useCaseClass);
        verify(existsByIdUseCase, times(1)).existsById(entityId);
    }
}
