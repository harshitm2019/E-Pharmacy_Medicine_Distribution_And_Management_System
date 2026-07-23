-- KEYS
-- reservation:<reservationId>

local reservation = redis.call(
    'GET',
    KEYS[1]
)

if not reservation then
    return 0
end

local data = cjson.decode(reservation)

for i,item in ipairs(data.items) do

    redis.call(
        'INCRBY',
        'stock:' .. item.medicineId,
        tonumber(item.quantity)
    )

end

redis.call(
    'DEL',
    KEYS[1]
)

redis.call(
    'ZREM',
    'active_reservations',
    data.reservationId
)

return 1