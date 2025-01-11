/*
  Warnings:

  - A unique constraint covering the columns `[place_id]` on the table `Location` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `place_api` to the `Location` table without a default value. This is not possible if the table is not empty.
  - Added the required column `place_id` to the `Location` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Location" ADD COLUMN     "categories" VARCHAR(100)[],
ADD COLUMN     "opening_hours" VARCHAR(255),
ADD COLUMN     "phone" VARCHAR(50),
ADD COLUMN     "place_api" VARCHAR(255) NOT NULL,
ADD COLUMN     "place_id" VARCHAR(100) NOT NULL,
ADD COLUMN     "website" VARCHAR(2083);

-- CreateIndex
CREATE UNIQUE INDEX "Location_place_id_key" ON "Location"("place_id");
