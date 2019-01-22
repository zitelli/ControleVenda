import java.util.List;
import org.junit.Test;

import br.com.ControleVenda.dao.CRUD;
import br.com.ControleVenda.dao.Pedido;

/**
 * 
 */

/**
 * @author junior
 *
 */
public class TestCRUD {

	/**
	 * Test method for CRUD - DisplayRecords at DataBase ControleVenda.
	 */
	@Test
	public void testDisplayRecords() {
		List<Pedido>viewPedidos = CRUD.displayRecords();
		if(viewPedidos != null & viewPedidos.size() > 0) {
			for(Pedido pedidoObj : viewPedidos) {
				System.out.println(pedidoObj.toString());
			}
		}

		//fail("Not yet implemented");
	      //check for equality
/*		  String str = "working fin";
	      assertEquals("working fine", str);
	      
	      int num = 6;
	      //check for false condition
	      assertFalse(num > 6);

	      //check for tru condition
	      assertTrue(num >= 6);

	      String temp = "";
	      //check for not null value
	      assertNotNull(temp);
*/

	}

	/**
	 * Test method for CRUD - CreateRecord() at DataBase ControleVenda.
	 */
	@Test
	public void testCreateRecord() {
		Pedido ped = new Pedido();
		ped.setCtr(1);
		ped.setData("209-01-23");
		ped.setProd("caneta");
		ped.setQtd(5);
		ped.setVlr(2.50);
		ped.setCod("TUR");
		CRUD.createRecord(ped);
	}
}
