# ---- Build stage ----
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# ---- Runtime stage ----
FROM nginx:alpine
COPY --from=build /app/dist/angular-ecommerce /usr/share/nginx/html
# SPA fallback
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
