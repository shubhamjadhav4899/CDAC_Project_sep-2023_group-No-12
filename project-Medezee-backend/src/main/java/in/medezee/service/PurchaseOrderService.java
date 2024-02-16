package in.medezee.service;

import java.util.List;

import in.medezee.exception.OrderException;
import in.medezee.modal.Address;
import in.medezee.modal.PurchaseOrder;
import in.medezee.modal.User;

public interface PurchaseOrderService {
	
	public PurchaseOrder createPurchaseOrder(User user,Address address);
	
	public PurchaseOrder findPurchaseOrderById(Long PurchaseOrderId)throws OrderException;
	
	public List<PurchaseOrder> userPurchaseOrderHistory(Long userId);
	
	public PurchaseOrder placePurchaseOrder(Long userId) throws OrderException;
	
	public PurchaseOrder shippedPurchaseOrder(Long PurchaseOrderId)throws OrderException;
	
	public PurchaseOrder deliveredPurchaseOrder(Long PurchaseOrderId) throws OrderException;
	
	public PurchaseOrder canceledPurchaseOrder(Long userId) throws OrderException;
	
}
