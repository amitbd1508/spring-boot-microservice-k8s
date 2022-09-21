package com.miniprojecttwo.productservice.dto;

import lombok.Data;

@Data
public class DeductInventoryRequest {
  long productId;
  long quantity;
}
