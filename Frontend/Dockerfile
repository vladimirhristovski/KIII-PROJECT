# --- Stage 1: Build the frontend ---
FROM node:18 AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

# --- Stage 2: Serve with NGINX ---
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
