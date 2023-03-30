
##? Run service dev
compose_dev_up_v1:
	docker-compose -f docker-compose.yml up -d --build

compose_dev_down_v1:
	docker-compose -f docker-compose.yml down

compose_dev_up_v2:
	docker compose -f docker-compose.yml up -d --build

compose_dev_down_v2:
	docker compose -f docker-compose.yml down