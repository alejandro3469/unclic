package mx.com.endtoend.infrastructure.logs.orders.calzada.business;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;
import mx.com.endtoend.infrastructure.logs.orders.GenericOrderLogPersistenceInterface;
import mx.com.endtoend.infrastructure.logs.orders.calzada.repositories.OrderDetailFinalStateLogFCalRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.repositories.OrderDetailPrevStateLogFCalRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.repositories.OrderFinalStateLogFCalRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.repositories.OrderLogFCalRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.repositories.OrderPrevStateLogFCalRepository;
import mx.com.endtoend.infrastructure.logs.orders.converters.OrderLogConverter;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailPreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderLogEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderPreviousStateEntity;

@Service
public class OrderLogCalzadaRepository implements GenericOrderLogPersistenceInterface {

	@Autowired
	private OrderLogConverter orderLogConverter;

	@Autowired
	private OrderLogFCalRepository orderLogFCalRepository;

	@Autowired
	private OrderDetailFinalStateLogFCalRepository orderDetailFinalStateLogFCalRepository;

	@Autowired
	private OrderDetailPrevStateLogFCalRepository orderDetailPrevStateLogFCalRepository;

	@Autowired
	private OrderFinalStateLogFCalRepository orderFinalStateLogFCalRepository;

	@Autowired
	private OrderPrevStateLogFCalRepository orderPrevStateLogFCalRepository;

	private final Logger LOG = LoggerFactory.getLogger(OrderLogCalzadaRepository.class);

	@Override
	public void saveOrderLog(OrderSummaryLogDto oderOrderSummaryLogDto) {
		try {
			LOG.info("saveOrderLog");
			OrderLogEntity orderLogEntity = orderLogConverter.orderSummaryToEntiy(oderOrderSummaryLogDto);
			orderLogEntity = orderLogFCalRepository.save(orderLogEntity);
			Long orderSummaryId = orderLogEntity.getId();

			OrderPreviousStateEntity orderPreviousStateEntity = orderLogConverter
					.orderSavedToEntity(oderOrderSummaryLogDto.getOrderSaved(), orderSummaryId);
			orderPreviousStateEntity = orderPrevStateLogFCalRepository.save(orderPreviousStateEntity);
			Long orderPrevId = orderPreviousStateEntity.getOrderId();

			List<OrderDetailPreviousStateEntity> orderDetailPrevStateList = oderOrderSummaryLogDto.getOrderSaved()
					.getOrderDetail().stream()
					.map(orderDetailDto -> orderLogConverter.orderDetailSavedToEntity(orderDetailDto, orderPrevId))
					.collect(Collectors.toList());
			orderDetailPrevStateLogFCalRepository.saveAll(orderDetailPrevStateList);

			OrderFinalStateEntity orderFinalStateEntity = orderLogConverter
					.orderUpdatedToEntity(oderOrderSummaryLogDto.getOrderUpdated(), orderSummaryId);
			orderFinalStateEntity = orderFinalStateLogFCalRepository.save(orderFinalStateEntity);
			Long orderFinalId = orderFinalStateEntity.getOrderId();

			List<OrderDetailFinalStateEntity> orderDetailFinalStateList = oderOrderSummaryLogDto.getOrderUpdated()
					.getOrderDetail().stream()
					.map(ordedrDetailDto -> orderLogConverter.orderDetailUpdatedToEntity(ordedrDetailDto, orderFinalId))
					.collect(Collectors.toList());
			orderDetailFinalStateLogFCalRepository.saveAll(orderDetailFinalStateList);

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}