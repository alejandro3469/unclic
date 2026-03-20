package mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.business;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;
import mx.com.endtoend.infrastructure.logs.orders.GenericOrderLogPersistenceInterface;
import mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.repositories.OrderDetailFinalStateLogCFragRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.repositories.OrderDetailPrevStateLogCFragRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.repositories.OrderFinalStateLogCFragRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.repositories.OrderLogCFragRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.repositories.OrderPrevStateLogCFragRepository;
import mx.com.endtoend.infrastructure.logs.orders.converters.OrderLogConverter;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailPreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderLogEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderPreviousStateEntity;

@Service
public class OrderLogFraguaRepository implements GenericOrderLogPersistenceInterface {

	@Autowired
	private OrderLogConverter orderLogConverter;

	@Autowired
	private OrderLogCFragRepository orderLogFCalRepository;

	@Autowired
	private OrderDetailFinalStateLogCFragRepository orderDetailFinalStateLogFCalRepository;

	@Autowired
	private OrderDetailPrevStateLogCFragRepository orderDetailPrevStateLogFCalRepository;

	@Autowired
	private OrderFinalStateLogCFragRepository orderFinalStateLogFCalRepository;

	@Autowired
	private OrderPrevStateLogCFragRepository orderPrevStateLogFCalRepository;

	private final Logger LOG = LoggerFactory.getLogger(OrderLogFraguaRepository.class);

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
