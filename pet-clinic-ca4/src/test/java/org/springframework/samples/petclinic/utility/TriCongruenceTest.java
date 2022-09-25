package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1arr[0] != t2arr[0]")
@ClauseDefinition(clause = 'b', def = "t1arr[1] != t2arr[1]")
@ClauseDefinition(clause = 'c', def = "t1arr[2] != t2arr[2]")
@ClauseDefinition(clause = 'd', def = "t1arr[0] < 0")
@ClauseDefinition(clause = 'e', def = "t1arr[0] + t1arr[1] < t1arr[2]")
class TriCongruenceTest {

	/** line 14 test CUTPNFP */
	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)

	//TFF
	@Test
	public void areCongruentTestUniqueTruePointA() {
		Triangle t1 = new Triangle(1, 2, 2);
		Triangle t2 = new Triangle(2, 2, 2);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		}
	)

	//FTF
	@Test
	public void areCongruentTestUniqueTruePointB() {
		Triangle t1 = new Triangle(1, 2, 3);
		Triangle t2 = new Triangle(1, 3, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)

	//FFT
	@Test
	public void areCongruentTestUniqueTruePointC() {
		Triangle t1 = new Triangle(2, 2, 3);
		Triangle t2 = new Triangle(2, 2, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)

	//FFF
	@Test
	public void areCongruentTestUniqueNearFalsePoint() {
		Triangle t1 = new Triangle(1, 1, 1);
		Triangle t2 = new Triangle(1, 1, 1);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	/** line 15 test CC */
	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = true)
		}
	)

	//TT
	@Test
	public void areCongruentTestClauseCoverageTrue() {
		Triangle t1 = new Triangle(-1, 1, 2);
		Triangle t2 = new Triangle(-1, 1, 2);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)

	//FF
	@Test
	public void areCongruentTestClauseCoverageFalse() {
		Triangle t1 = new Triangle(1, 6, 7);
		Triangle t2 = new Triangle(1, 6, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	/** line 15 test CACC */
	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false)
		},
		predicateValue = true
	)

	//TF
	//not feasible

	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		},
		predicateValue = false
	)

	@CACC(
		predicate = "d + e",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		},
		predicateValue = false
	)

	//FF
	@Test
	public void areCongruentTestCACCFalse() {
		Triangle t1 = new Triangle(1, 6, 7);
		Triangle t2 = new Triangle(1, 6, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	@CACC(
		predicate = "d + e",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true)
		},
		predicateValue = false
	)

	//FT
	@Test
	public void areCongruentTestCACCTrueE() {
		Triangle t1 = new Triangle(1, 1, 3);
		Triangle t2 = new Triangle(1, 1, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	/** question 2*/

	//for implicant ab
	@UniqueTruePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "ab",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
		}
	)

	@UniqueTruePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "ab",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	@UniqueTruePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "ab",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = false),
		}
	)

	//NFP for TTFT for clause a
	@NearFalsePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "ab",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	//NFP for TTFT for clause b
	@NearFalsePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "ab",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	//for implicant cd
	@UniqueTruePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "cd",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	@UniqueTruePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "cd",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	@UniqueTruePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "cd",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	//NFP for FFTT for clause c
	@NearFalsePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "cd",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = true),
		}
	)

	//NFP for TTFT for clause d
	@NearFalsePoint(
		predicate = "ab + cd",
		dnf = "ab + cd",
		implicant = "cd",
		clause = 'd',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = false),
		}
	)

	//CUTPNFP = {TTFT (unique true), FTFT (near TTFT), TFFT (near TTFT), FFTT (unique true), FFFT (near FFTT), FFTF (near FFTT)}
	//UTPC = {TTFF, TTFT, TTTF, FFTT, FTTT, TFTT}
	//uncovered = {TTFF, TTTF, FTTT, TFTT}

	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d) {
		boolean f = (a && b) || (c && d);
		return f;
	}

}
