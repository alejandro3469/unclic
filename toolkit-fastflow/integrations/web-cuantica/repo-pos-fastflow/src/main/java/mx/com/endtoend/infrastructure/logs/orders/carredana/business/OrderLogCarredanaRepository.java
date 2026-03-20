package mx.com.endtoend.infrastructure.logs.orders.carredana.business;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;
import mx.com.endtoend.infrastructure.logs.orders.GenericOrderLogPersistenceInterface;
import mx.com.endtoend.infrastructure.logs.orders.carredana.repositories.OrderDetailFinalStateLogFCarRepository;
import mx.com.endtoend.infrastructure.logs.orders.carredana.repositories.OrderDetailPrevStateLogFCarRepository;
import mx.com.endtoend.infrastructure.logs.orders.carredana.repositories.OrderFinalStateLogFCarRepository;
import mx.com.endtoend.infrastructure.logs.orders.carredana.repositories.OrderLogFCarRepository;
import mx.com.endtoend.infrastructure.logs.orders.carredana.repositories.OrderPrevStateLogFCarRepository;
import mx.com.endtoend.infrastructure.logs.orders.converters.OrderLogConverter;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailPreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderLogEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderPreviousStateEntity;

@Service
public class OrderLogCarredanaRepository implements GenericOrderLogPersistenceInterface {

	@Autowired
	private OrderLogConverter orderLogConverter;

	@Autowired
	private OrderLogFCarRepository orderLogFCalRepository;

	@Autowired
	private OrderDetailFinalStateLogFCarRepository orderDetailFinalStateLogFCalRepository;

	@Autowired
	private OrderDetailPrevStateLogFCarRepository orderDetailPrevStateLogFCalRepository;

	@Autowired
	private OrderFinalStateLogFCarRepository orderFinalStateLogFCalRepository;

	@Autowired
	private OrderPrevStateLogFCarRepository orderPrevStateLogFCalRepository;

	private final Logger LOG = LoggerFactory.getLogger(OrderLogCarredanaRepository.class);

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
