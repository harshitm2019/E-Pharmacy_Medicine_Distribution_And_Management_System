-- KEYS:
-- 1..N        -> stock:<medicineId>
-- N+1         -> reservation:<reservationId>

-- ARGV:
-- 1..N        -> quantities
-- N+1         -> reservationId
-- N+2         -> reservationJson

local stockCount = #KEYS - 1
local reservationKey = KEYS[#KEYS]

--------------------------------------------------
-- Check stock first
--------------------------------------------------

for i = 1, stockCount do

    local stock = redis.call('GET', KEYS[i])

    if not stock then
        return -i
    end

    if tonumber(stock) < tonumber(ARGV[i]) then
        return i
    end

end

--------------------------------------------------
-- Reserve stock
--------------------------------------------------

for i = 1, stockCount do

    redis.call(
        'DECRBY',
        KEYS[i],
        tonumber(ARGV[i])
    )

end

--------------------------------------------------
-- Save reservation
--------------------------------------------------

redis.call(
    'SET',
    reservationKey,
    ARGV[stockCount + 2]
)

local reservation = cjson.decode(ARGV[stockCount + 2])

redis.call(
    'ZADD',
    'active_reservations',
    reservation.expiresAtEpoch,
    reservation.reservationId
)

return 1