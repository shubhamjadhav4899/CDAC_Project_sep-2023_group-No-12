package in.medezee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.medezee.exception.OrderException;
import in.medezee.modal.Address;
import in.medezee.modal.PurchaseOrder;
import in.medezee.modal.User;
import in.medezee.repository.CartRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImplementation implements PurchaseOrderService {

	private final CartRepository cartRepository;
	
	private final CartService cartService;
	
	private final PurchaseOrderService orderService;
	@Override
	public PurchaseOrder createPurchaseOrder(User user, Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder findPurchaseOrderById(Long PurchaseOrderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchaseOrder> userPurchaseOrderHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder placePurchaseOrder(Long userId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder shippedPurchaseOrder(Long PurchaseOrderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder deliveredPurchaseOrder(Long PurchaseOrderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder canceledPurchaseOrder(Long userId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

}
