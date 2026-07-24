-- KEYS
-- reservation:<reservationId>

-- ARGV
-- reservationId

local reservationKey = KEYS[1]
local activeReservationsKey = KEYS[2]
local reservationId = ARGV[1]

local reservation = redis.call('GET', reservationKey)

if not reservation then
    return nil
end

redis.call('DEL', reservationKey)
redis.call('ZREM', activeReservationsKey, reservationId)

return reservation