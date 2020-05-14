package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {
  private TorpedoStore mock_1;
  private TorpedoStore mock_2;
  private GT4500 ship;

  @BeforeEach
  public void init(){
	  mock_1 = mock(TorpedoStore.class);
	  mock_2 = mock(TorpedoStore.class);
	    this.ship = new GT4500();
	    this.ship.injectDependencies(mock_1, mock_2, false);
  }

  @Test
  public void fireTorpedo_Single_Success(){
	// Arrange
	    when(mock_1.fire(1)).thenReturn(true);
	    when(mock_2.fire(1)).thenReturn(true);
	    // Act
	    boolean results = ship.fireTorpedo(FiringMode.SINGLE);

	    // Assert
	    assertEquals(true, results);
	    verify(mock_1, times(1)).fire(1);
	    verify(mock_2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
	// Arrange
	    when(mock_1.fire(1)).thenReturn(true);
	    when(mock_2.fire(1)).thenReturn(true);
	    // Act
	    boolean results = ship.fireTorpedo(FiringMode.ALL);
	    // Assert
	    assertEquals(false, results);

  }

 
  
  @Test
  public void AlternatingfTorpedo_Succeed(){

    when(mock_1.fire(1)).thenReturn(true);
    when(mock_2.fire(1)).thenReturn(true);

    boolean result_1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result_2 = ship.fireTorpedo(FiringMode.SINGLE);
    
    boolean res = result_1 & result_2;

    assertEquals(true, res);

    verify(mock_1, times(1)).fire(1);
    verify(mock_2, times(1)).fire(1);
  }
  
  @Test
  public void firstEmptySecondSuccess(){
    // Arrange
    when(mock_1.isEmpty()).thenReturn(true);
    when(mock_2.fire(1)).thenReturn(true);
    // Act
    boolean result_1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result_2 = ship.fireTorpedo(FiringMode.SINGLE);
    

    // Assert
    assertEquals(true, result_2 & result_1);
    
    verify(mock_1, times(2)).isEmpty();
    verify(mock_2, times(2)).fire(1);
  }
  
  @Test
  public void firstStoreIsFired(){

    when(mock_1.fire(1)).thenReturn(true);
    when(mock_2.isEmpty()).thenReturn(true);
 
    boolean result_1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result_2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result_1 & result_2);
   
    verify(mock_1, times(2)).fire(1);
    verify(mock_2, times(1)).isEmpty();
  
  }
  
  @Test
  public void allStoreFailure(){
    // Arrange
    when(mock_1.fire(1)).thenReturn(false);
    when(mock_2.fire(1)).thenReturn(false);
    // Act
    boolean results = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, results);

  }
  
  @Test
  public void firstStoreFailure(){
    // Arrange
    when(mock_1.fire(1)).thenReturn(false);
    when(mock_2.fire(1)).thenReturn(false);
    // Act
    boolean results = ship.fireTorpedo(FiringMode.SINGLE);;
    // Assert
    assertEquals(false, results);
    verify(mock_1, times(1)).fire(1);
    verify(mock_2, times(0)).fire(1);
  }
  
 
  
  
  
  
  @Test
  public void fireLazerSingleFailed(){
    // Arrange
    when(mock_1.isEmpty()).thenReturn(true);

    // Act
    boolean res = ship.fireLaser(FiringMode.SINGLE);
    // Assert
    assertEquals(false, res);
    verify(mock_1, times(0)).fire(1);

  }
  @Test
  public void fireLazerALL(){
    // Arrange
    when(mock_1.fire(1)).thenReturn(true);
    when(mock_2.fire(1)).thenReturn(true);

    // Act
    boolean res = ship.fireLaser(FiringMode.ALL);
    // Assert
    assertEquals(false, res);
    verify(mock_1, times(0)).fire(1);
    verify(mock_2, times(0)).fire(1);

  }
  
}