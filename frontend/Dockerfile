# Stage 1: Build the application
FROM node:lts-slim AS build

WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build

# Stage 2: Run the application
FROM node:lts-slim

WORKDIR /app
COPY --from=build /app ./
RUN npm install --production --legacy-peer-deps
EXPOSE 3000

CMD ["npm", "start"]
