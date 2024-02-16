package in.medezee.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsDetails {
	private String paymentMethod;
	
	private String status;
	
	private String paymentId;
	
	private String razorpayPaymentLinkId;
	
	private String razorpayLinkStatus;
	
	private String razorpayPaymentLinkReferenceId;
	
	private String razorpayPaymentId;
}
