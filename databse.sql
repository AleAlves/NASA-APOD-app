CREATE TABLE IF NOT EXISTS fav_apod (
    _id integer primary key autoincrement,
    copyright text,
    date_ text,
    explanation text,
    hdurl text,
    media_type text,
    service_version text,
    title text,
    url text,
);

CREATE TABLE IF NOT EXISTS device (
    _imei text primary key,
    model_name text,
    screen_size text,
    manufacturer text,
    rate_value integer
) 