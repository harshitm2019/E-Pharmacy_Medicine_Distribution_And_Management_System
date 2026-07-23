-- KEYS
-- reservation:<reservationId>

-- ARGV
-- reservationId

local reservation = redis.call(
    'GET',
    KEYS[1]
)

if not reservation then
    return 0
end

redis.call(
    'DEL',
    KEYS[1]
)

redis.call(
    'ZREM',
    'active_reservations',
    ARGV[1]
)
return 1