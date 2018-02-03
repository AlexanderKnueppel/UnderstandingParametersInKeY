#Verzeichnis mit den Dateien
base <- "C:/Users/Carsten/Desktop/Res/"

#STOP AT
filename <- paste(sep="",base,"exp_01_02.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..StopAtDefault)
dataBC <- as.numeric(ergebnisse$Closed..StopAtUnclosable)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..StopAtDefault == Closed..StopAtUnclosable & Closed..StopAtUnclosable =="x")
dataAN <- dataFilter$Nodes.StopAtDefault
dataBN <- dataFilter$Nodes.StopAtUnclosable
t.test(dataAN, dataBN, paired=TRUE)



#ONE STEP SIMPLIFICATION
filename <- paste(sep="",base,"exp_03_04.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..OneStepSimplificationEnabled)
dataBC <- as.numeric(ergebnisse$Closed..OneStepSimplificationDisabled)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..OneStepSimplificationEnabled == Closed..OneStepSimplificationDisabled & Closed..OneStepSimplificationDisabled =="x")
dataAN <- dataFilter$Nodes.OneStepSimplificationEnabled
dataBN <- dataFilter$Nodes.OneStepSimplificationDisabled
t.test(dataAN, dataBN, paired=TRUE, alternative = "greater")
t.test(dataAN, dataBN, paired=TRUE, alternative = "less")



#PROOF SPLITTING
filename <- paste(sep="",base,"exp_05_06.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ProofSplittingDelayed)
dataBC <- as.numeric(ergebnisse$Closed..ProofSplittingFree)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..ProofSplittingDelayed == Closed..ProofSplittingDelayed & Closed..ProofSplittingDelayed =="x")
dataAN <- dataFilter$Nodes.ProofSplittingDelayed
dataBN <- dataFilter$Nodes.ProofSplittingFree
t.test(dataAN, dataBN, paired=TRUE, alternative = "greater")
t.test(dataAN, dataBN, paired=TRUE, alternative = "less")


filename <- paste(sep="",base,"exp_07_09.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ProofSplittingOff)
dataBC <- as.numeric(ergebnisse$Closed..ProofSplittingFree)
mcnemar.test(dataAC,dataBC)
t.test(dataAC,dataBC, paired=TRUE, alternative = "less")
dataFilter <- subset(ergebnisse, Closed..ProofSplittingOff == Closed..ProofSplittingFree & Closed..ProofSplittingOff =="x")
dataAN <- dataFilter$Nodes.ProofSplittingOff
dataBN <- dataFilter$Nodes.ProofSplittingFree
t.test(dataAN, dataBN, paired=TRUE, alternative = "greater")
t.test(dataAN, dataBN, paired=TRUE, alternative = "less")


filename <- paste(sep="",base,"exp_08_10.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ProofSplittingOff)
dataBC <- as.numeric(ergebnisse$Closed..ProofSplittingDelayed)
mcnemar.test(dataAC,dataBC)
t.test(dataAC,dataBC, paired=TRUE, alternative = "greater")
dataFilter <- subset(ergebnisse, Closed..ProofSplittingOff == Closed..ProofSplittingDelayed & Closed..ProofSplittingOff =="x")
dataAN <- dataFilter$Nodes.ProofSplittingOff
dataBN <- dataFilter$Nodes.ProofSplittingDelayed
t.test(dataAN, dataBN, paired=TRUE, alternative = "greater")
t.test(dataAN, dataBN, paired=TRUE, alternative = "less")



#LOOP TREATMENT
filename <- paste(sep="",base,"exp_11_12.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..LoopTreatmentInvariant)
dataBC <- as.numeric(ergebnisse$Closed..LoopTreatmentLoopScopeInvariant)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..LoopTreatmentInvariant == Closed..LoopTreatmentLoopScopeInvariant & Closed..LoopTreatmentInvariant =="x")
dataAN <- dataFilter$Nodes.LoopTreatmentInvariant
dataBN <- dataFilter$Nodes.LoopTreatmentLoopScopeInvariant
t.test(dataAN, dataBN, paired=TRUE)



#DEPENDENCY CONTRACTS
filename <- paste(sep="",base,"exp_13_14.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..DependencyContractsOn)
dataBC <- as.numeric(ergebnisse$Closed..DependencyContractsOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..DependencyContractsOn == Closed..DependencyContractsOff & Closed..DependencyContractsOn =="x")
dataAN <- dataFilter$Nodes.DependencyContractsOn
dataBN <- dataFilter$Nodes.DependencyContractsOff
t.test(dataAN, dataBN, paired=TRUE)



#QUERY TREATMENT
filename <- paste(sep="",base,"exp_15_17.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QueryTreatmentOn)
dataBC <- as.numeric(ergebnisse$Closed..QueryTreatmentRestricted)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QueryTreatmentOn == Closed..QueryTreatmentRestricted & Closed..QueryTreatmentOn =="x")
dataAN <- dataFilter$Nodes.QueryTreatmentOn
dataBN <- dataFilter$Nodes.QueryTreatmentRestricted
t.test(dataAN, dataBN, paired=TRUE)


filename <- paste(sep="",base,"exp_16_18.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QueryTreatmentOn)
dataBC <- as.numeric(ergebnisse$Closed..QueryTreatmentOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QueryTreatmentOn == Closed..QueryTreatmentOff & Closed..QueryTreatmentOn =="x")
dataAN <- dataFilter$Nodes.QueryTreatmentOn
dataBN <- dataFilter$Nodes.QueryTreatmentOff
t.test(dataAN, dataBN, paired=TRUE)


filename <- paste(sep="",base,"exp_19.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QueryTreatmentOff)
dataBC <- as.numeric(ergebnisse$Closed..QueryTreatmentRestricted)
mcnemar.test(dataAC,dataBC)
filename <- paste(sep="",base,"exp_20_21.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QueryTreatmentRestricted)
dataBC <- as.numeric(ergebnisse$Closed..QueryTreatmentOn)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QueryTreatmentRestricted == Closed..QueryTreatmentOn & Closed..QueryTreatmentRestricted =="x")
dataAN <- dataFilter$Nodes.QueryTreatmentRestricted
dataBN <- dataFilter$Nodes.QueryTreatmentOn
t.test(dataAN, dataBN, paired=TRUE)



#EXPAND LOCAL QUERIES
filename <- paste(sep="",base,"exp_22.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataFilter <- subset(ergebnisse, Closed..ExpandLocalQueriesOn == Closed..ExpandLocalQueriesOff & Closed..ExpandLocalQueriesOn =="x")
dataAN <- dataFilter$Nodes.ExpandLocalQueriesOn
dataBN <- dataFilter$Nodes.ExpandLocalQueriesOff
t.test(dataAN, dataBN, paired=TRUE,alternative = "less")
t.test(dataAN, dataBN, paired=TRUE,alternative = "greater")
filename <- paste(sep="",base,"exp_23.txt"
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ExpandLocalQueriesOn)
dataBC <- as.numeric(ergebnisse$Closed..ExpandLocalQueriesOff)
mcnemar.test(dataAC,dataBC)



#ARITHMETIC TREATMENT
filename <- paste(sep="",base,"exp_24.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ArithmeticTreatmentBasic)
dataBC <- as.numeric(ergebnisse$Closed..ArithmeticTreatmentDefOps)
mcnemar.test(dataAC,dataBC)


filename <- paste(sep="",base,"exp_25.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ArithmeticTreatmentDefOps)
dataBC <- as.numeric(ergebnisse$Closed..ArithmeticTreatmentModelSearch)
mcnemar.test(dataAC,dataBC)



#QUANTIFIER TREATMENT
filename <- paste(sep="",base,"exp_26_29.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNone)
dataBC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNoSplits)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QuantifierTreatmentNone == Closed..QuantifierTreatmentNoSplits & Closed..QuantifierTreatmentNone =="x")
dataAN <- dataFilter$Nodes.QuantifierTreatmentNone
dataBN <- dataFilter$Nodes.QuantifierTreatmentNoSplits
t.test(dataAN, dataBN, paired=TRUE)

filename <- paste(sep="",base,"exp_27_30.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNone)
dataBC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNoSplitsWithProgs)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QuantifierTreatmentNone == Closed..QuantifierTreatmentNoSplitsWithProgs & Closed..QuantifierTreatmentNone =="x")
dataAN <- dataFilter$Nodes.QuantifierTreatmentNone
dataBN <- dataFilter$Nodes.QuantifierTreatmentNoSplitsWithProgs
t.test(dataAN, dataBN, paired=TRUE)

filename <- paste(sep="",base,"exp_28_31.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNone)
dataBC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentFree)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QuantifierTreatmentNone == Closed..QuantifierTreatmentFree & Closed..QuantifierTreatmentNone =="x")
dataAN <- dataFilter$Nodes.QuantifierTreatmentNone
dataBN <- dataFilter$Nodes.QuantifierTreatmentFree
t.test(dataAN, dataBN, paired=TRUE)

filename <- paste(sep="",base,"exp_34_35.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentFree)
dataBC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNoSplitsWithProgs)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QuantifierTreatmentFree == Closed..QuantifierTreatmentNoSplitsWithProgs & Closed..QuantifierTreatmentFree =="x")
dataAN <- dataFilter$Nodes.QuantifierTreatmentFree
dataBN <- dataFilter$Nodes.QuantifierTreatmentNoSplitsWithProgs
t.test(dataAN, dataBN, paired=TRUE,alternative = "less")
t.test(dataAN, dataBN, paired=TRUE,alternative = "greater")

filename <- paste(sep="",base,"exp_33_36.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNoSplitsWithProgs)
dataBC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNoSplits)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QuantifierTreatmentNoSplitsWithProgs == Closed..QuantifierTreatmentNoSplits & Closed..QuantifierTreatmentNoSplitsWithProgs =="x")
dataAN <- dataFilter$Nodes.QuantifierTreatmentNoSplitsWithProgs
dataBN <- dataFilter$Nodes.QuantifierTreatmentNoSplits
t.test(dataAN, dataBN, paired=TRUE,alternative = "less")
t.test(dataAN, dataBN, paired=TRUE,alternative = "greater")

filename <- paste(sep="",base,"exp_32_37.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNoSplits)
dataBC <- as.numeric(ergebnisse$Closed..QuantifierTreatmentNone)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..QuantifierTreatmentNoSplits == Closed..QuantifierTreatmentNone & Closed..QuantifierTreatmentNoSplits =="x")
dataAN <- dataFilter$Nodes.QuantifierTreatmentNoSplits
dataBN <- dataFilter$Nodes.QuantifierTreatmentNone
t.test(dataAN, dataBN, paired=TRUE,alternative = "less")
t.test(dataAN, dataBN, paired=TRUE,alternative = "greater")



#CLASS AXIOM RULE
filename <- paste(sep="",base,"exp_38_40.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ClassAxiomRuleFree)
dataBC <- as.numeric(ergebnisse$Closed..ClassAxiomRuleDelayed)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..ClassAxiomRuleFree == Closed..ClassAxiomRuleDelayed & Closed..ClassAxiomRuleFree =="x")
dataAN <- dataFilter$Nodes.ClassAxiomRuleFree
dataBN <- dataFilter$Nodes.ClassAxiomRuleDelayed
t.test(dataAN, dataBN, paired=TRUE)

filename <- paste(sep="",base,"exp_39_41.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..ClassAxiomRuleFree)
dataBC <- as.numeric(ergebnisse$Closed..ClassAxiomRuleOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..ClassAxiomRuleFree == Closed..ClassAxiomRuleOff & Closed..ClassAxiomRuleFree =="x")
dataAN <- dataFilter$Nodes.ClassAxiomRuleFree
dataBN <- dataFilter$Nodes.ClassAxiomRuleOff
t.test(dataAN, dataBN, paired=TRUE)



#STRINGS
filename <- paste(sep="",base,"exp_42_43.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..StringsOn)
dataBC <- as.numeric(ergebnisse$Closed..StringsOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..StringsOn == Closed..StringsOff & Closed..StringsOn =="x")
dataAN <- dataFilter$Nodes.StringsOn
dataBN <- dataFilter$Nodes.StringsOff
t.test(dataAN, dataBN, paired=TRUE)



#BigInt
filename <- paste(sep="",base,"exp_44_45.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..BigintOn)
dataBC <- as.numeric(ergebnisse$Closed..BigintOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..BigintOn == Closed..BigintOff & Closed..BigintOn =="x")
dataAN <- dataFilter$Nodes.BigintOn
dataBN <- dataFilter$Nodes.BigintOff
t.test(dataAN, dataBN, paired=TRUE)



#IntegerSimplificationRules
filename <- paste(sep="",base,"exp_46_47.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..IntegerSimplificationRulesFull)
dataBC <- as.numeric(ergebnisse$Closed..IntegerSimplificationRulesMinimal)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..IntegerSimplificationRulesFull == Closed..IntegerSimplificationRulesMinimal & Closed..IntegerSimplificationRulesFull =="x")
dataAN <- dataFilter$Nodes.IntegerSimplificationRulesFull
dataBN <- dataFilter$Nodes.IntegerSimplificationRulesMinimal
t.test(dataAN, dataBN, paired=TRUE)



#Sequences
filename <- paste(sep="",base,"exp_48_49.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..SequencesOn)
dataBC <- as.numeric(ergebnisse$Closed..SequencesOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..SequencesOn == Closed..SequencesOff & Closed..SequencesOn =="x")
dataAN <- dataFilter$Nodes.SequencesOn
dataBN <- dataFilter$Nodes.SequencesOff
t.test(dataAN, dataBN, paired=TRUE)



#MoreSeqRules
filename <- paste(sep="",base,"exp_50_51.txt")
ergebnisse <- read.table(filename, header = TRUE, sep = '|')
dataAC <- as.numeric(ergebnisse$Closed..MoreSeqRulesOn)
dataBC <- as.numeric(ergebnisse$Closed..MoreSeqRulesOff)
mcnemar.test(dataAC,dataBC)
dataFilter <- subset(ergebnisse, Closed..MoreSeqRulesOn == Closed..MoreSeqRulesOff & Closed..MoreSeqRulesOn =="x")
dataAN <- dataFilter$Nodes.MoreSeqRulesOn
dataBN <- dataFilter$Nodes.MoreSeqRulesOff
t.test(dataAN, dataBN, paired=TRUE)

