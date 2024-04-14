CREATE TABLE Member (
    MemberID SERIAL PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(20),
    Address VARCHAR(255),
    CurrentWeight DECIMAL(5,2),
    WeightGoal DECIMAL(5,2),
    HealthMetrics TEXT
);

CREATE TABLE Trainer (
    TrainerID SERIAL PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Specialization VARCHAR(100)
);

CREATE TABLE AdministrativeStaff (
    StaffID SERIAL PRIMARY KEY,
    Name VARCHAR(100),
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Role VARCHAR(100)
);

CREATE TABLE PersonalTrainingSession (
    SessionID SERIAL PRIMARY KEY,
    TrainerID INT,
    MemberID INT,
    Date DATE,
    Time TIME,
    Duration INT,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);

CREATE TABLE GroupFitness (
    GroupID SERIAL PRIMARY KEY,
    Name VARCHAR(100),
    TrainerID INT,
    Date DATE,
    Time TIME,
    Duration INT,
    MaxCapacity INT,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);

CREATE TABLE Room (
    RoomID SERIAL PRIMARY KEY,
    RoomName VARCHAR(100),
    Capacity INT
);

CREATE TABLE Equipment (
    EquipmentID SERIAL PRIMARY KEY,
    EquipmentName VARCHAR(100),
    LastMaintenanceDate DATE
);

CREATE TABLE ClassSchedule (
    ScheduleID SERIAL PRIMARY KEY,
    GroupID INT,
    RoomID INT,
    Date DATE,
    Time TIME,
    FOREIGN KEY (GroupID) REFERENCES GroupFitness(GroupID),
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID)
);

CREATE TABLE BillingPayment (
    TransactionID SERIAL PRIMARY KEY,
    MemberID INT,
    Amount DECIMAL(10,2),
    Date DATE,
    PaymentMethod VARCHAR(100),
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID)
);


CREATE TABLE TrainerSchedule (
    ScheduleID SERIAL PRIMARY KEY,
    TrainerID INT,
    Date DATE,
    Time TIME,
    Availability BOOLEAN,
    FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID)
);
